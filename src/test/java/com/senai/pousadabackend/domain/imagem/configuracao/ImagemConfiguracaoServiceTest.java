package com.senai.pousadabackend.domain.imagem.configuracao;

import com.senai.pousadabackend.domain.imagem.configuracao.service.ImagemConfiguracaoService;
import com.senai.pousadabackend.domain.imagem.quarto.dto.ResultadoUploadDTO;
import com.senai.pousadabackend.domain.tema.Tema;
import com.senai.pousadabackend.domain.tema.TemaService;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.infraestructure.imagem.MinioDeleteClient;
import com.senai.pousadabackend.infraestructure.imagem.MinioUploadClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ImagemConfiguracaoServiceTest {

    @Mock
    private TemaService temaService;

    @Mock
    private MinioUploadClient minioUploadClient;

    @Mock
    private MinioDeleteClient minioDeleteClient;

    @Mock
    private ImagemConfiguracaoRepository repository;

    private ImagemConfiguracaoService service;

    @BeforeEach
    void setUp() {
        service = new ImagemConfiguracaoService(temaService, minioUploadClient, minioDeleteClient, repository);
    }

    private Tema temaPadrao() {
        return Tema.builder().id(1L).build();
    }

    private MockMultipartFile arquivoValido() {
        return new MockMultipartFile(
                "imagem", "foto.jpg",
                "image/jpeg",
                new byte[1024]
        );
    }

    private MockMultipartFile arquivoGrande() {
        return new MockMultipartFile(
                "imagem", "foto-grande.jpg",
                "image/jpeg",
                new byte[6 * 1024 * 1024]
        );
    }

    @Test
    void uploadImagem_arquivoValido_salvaERetornaUrl() {
        MockMultipartFile arquivo = arquivoValido();
        Tema tema = temaPadrao();

        when(temaService.buscarPorId(1L)).thenReturn(tema);
        when(minioUploadClient.uploadImagem(any())).thenReturn(
                ResultadoUploadDTO.builder()
                        .objectName("file-001")
                        .url("http://minio/imagens/foto.jpg")
                        .build()
        );

        String url = service.uploadImagem(List.of(arquivo), 1L);

        assertThat(url).isEqualTo("http://minio/imagens/foto.jpg");
        verify(repository).save(any(ImagemConfiguracao.class));
    }

    @Test
    void uploadImagem_arquivoGrande_lancaBusinessException() {
        MockMultipartFile arquivo = arquivoGrande();

        assertThatThrownBy(() -> service.uploadImagem(List.of(arquivo), 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("5MB");
    }

    @Test
    void uploadImagem_multiplosArquivos_salvaTodos() {
        MockMultipartFile arquivo1 = arquivoValido();
        MockMultipartFile arquivo2 = new MockMultipartFile(
                "imagem2", "foto2.jpg", "image/jpeg", new byte[512]
        );
        Tema tema = temaPadrao();

        when(temaService.buscarPorId(1L)).thenReturn(tema);
        when(minioUploadClient.uploadImagem(any()))
                .thenReturn(ResultadoUploadDTO.builder()
                        .objectName("file-001")
                        .url("http://minio/foto1.jpg")
                        .build())
                .thenReturn(ResultadoUploadDTO.builder()
                        .objectName("file-002")
                        .url("http://minio/foto2.jpg")
                        .build());

        String url = service.uploadImagem(List.of(arquivo1, arquivo2), 1L);

        assertThat(url).isEqualTo("http://minio/foto2.jpg");
        verify(repository, times(2)).save(any(ImagemConfiguracao.class));
    }

    @Test
    void listarPor_retornaListaDeImagens() {
        List<ImagemConfiguracao> imagens = List.of(
                ImagemConfiguracao.builder().id(1L).url("http://url1").fileId("f1").build(),
                ImagemConfiguracao.builder().id(2L).url("http://url2").fileId("f2").build()
        );

        when(repository.listarPor(1L)).thenReturn(imagens);

        List<ImagemConfiguracao> resultado = service.listarPor(1L);

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getUrl()).isEqualTo("http://url1");
    }

    @Test
    void deletar_imagemValida_deletaDoMinioERepo() {
        ImagemConfiguracao imagem = ImagemConfiguracao.builder()
                .id(1L)
                .url("http://minio/foto.jpg")
                .fileId("file-001")
                .build();

        service.deletar(imagem);

        verify(minioDeleteClient).deletarImagem("file-001");
        verify(repository).deleteById(1L);
    }

    @Test
    void deletar_semFileId_lancaBusinessException() {
        ImagemConfiguracao imagem = ImagemConfiguracao.builder()
                .id(1L)
                .url("http://minio/foto.jpg")
                .build();

        assertThatThrownBy(() -> service.deletar(imagem))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("id do arquivo");

        verify(minioDeleteClient, never()).deletarImagem(any());
        verify(repository, never()).deleteById(any());
    }
}
