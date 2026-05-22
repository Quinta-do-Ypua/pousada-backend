package com.senai.pousadabackend.domain.imagem.quarto.service;

import com.senai.pousadabackend.domain.imagem.quarto.ImagemQuarto;
import com.senai.pousadabackend.domain.imagem.quarto.ImagemQuartoRepository;
import com.senai.pousadabackend.domain.imagem.quarto.dto.ResultadoUploadDTO;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoService;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.infraestructure.imagem.MinioDeleteClient;
import com.senai.pousadabackend.infraestructure.imagem.MinioUploadClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImagemQuartoServiceTest {

    @Mock
    private QuartoService quartoService;

    @Mock
    private MinioDeleteClient minioDeleteClient;

    @Mock
    private MinioUploadClient minioUploadClient;

    @Mock
    private ImagemQuartoRepository repository;

    private ImagemQuartoService service;

    @BeforeEach
    void setUp() {
        service = new ImagemQuartoService(quartoService, minioDeleteClient, minioUploadClient, repository);
    }

    private Quarto quartoPadrao() {
        Quarto q = new Quarto();
        q.setId(1L);
        q.setNome("Suite 01");
        return q;
    }

    private MockMultipartFile imagemValida() {
        return new MockMultipartFile(
                "imagem", "foto.jpg", "image/jpeg", new byte[1024]
        );
    }

    private MockMultipartFile imagemGrande() {
        return new MockMultipartFile(
                "imagem", "foto.jpg", "image/jpeg", new byte[6 * 1024 * 1024]
        );
    }

    @Test
    void uploadImagem_sucesso_salvaImagem() {
        Quarto quarto = quartoPadrao();
        when(quartoService.buscarPorId(1L)).thenReturn(quarto);
        when(minioUploadClient.uploadImagem(any(MultipartFile.class)))
                .thenReturn(new ResultadoUploadDTO("file-id-123", "http://minio/foto.jpg"));

        service.uploadImagem(List.of(imagemValida()), 1L);

        verify(repository).save(any(ImagemQuarto.class));
        verify(minioUploadClient).uploadImagem(any(MultipartFile.class));
    }

    @Test
    void uploadImagem_imagemMuitoGrande_lancaBusinessException() {
        assertThatThrownBy(() -> service.uploadImagem(List.of(imagemGrande()), 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("5MB");
    }

    @Test
    void uploadImagem_multiplas_salvaTodasAsImagens() {
        Quarto quarto = quartoPadrao();
        when(quartoService.buscarPorId(1L)).thenReturn(quarto);
        when(minioUploadClient.uploadImagem(any(MultipartFile.class)))
                .thenReturn(new ResultadoUploadDTO("file-id", "http://url"))
                .thenReturn(new ResultadoUploadDTO("file-id-2", "http://url2"));

        service.uploadImagem(List.of(imagemValida(), imagemValida()), 1L);

        verify(repository, times(2)).save(any(ImagemQuarto.class));
    }

    @Test
    void listarPor_retornaImagensDoBancoParaQuarto() {
        List<ImagemQuarto> imagens = List.of(new ImagemQuarto());
        when(repository.listarPor(1L)).thenReturn(imagens);

        List<ImagemQuarto> resultado = service.listarPor(1L);

        assertThat(resultado).hasSize(1);
    }

    @Test
    void deletar_sucesso_deletaDoMinioEDoBanco() {
        ImagemQuarto imagem = ImagemQuarto.builder()
                .id(1L)
                .fileId("file-id-123")
                .url("http://minio/foto.jpg")
                .build();

        service.deletar(imagem);

        verify(minioDeleteClient).deletarImagem("file-id-123");
        verify(repository).deleteById(1L);
    }

    @Test
    void deletar_fileIdNulo_lancaBusinessException() {
        ImagemQuarto imagem = ImagemQuarto.builder()
                .id(1L)
                .url("http://url")
                .build();

        assertThatThrownBy(() -> service.deletar(imagem))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("id do arquivo");
    }

    @Test
    void deletar_urlNula_lancaBusinessException() {
        ImagemQuarto imagem = ImagemQuarto.builder()
                .id(1L)
                .fileId("file-id")
                .build();

        assertThatThrownBy(() -> service.deletar(imagem))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("url");
    }
}
