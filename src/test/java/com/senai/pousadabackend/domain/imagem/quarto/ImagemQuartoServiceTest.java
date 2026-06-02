package com.senai.pousadabackend.domain.imagem.quarto;

import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.imagem.quarto.dto.ResultadoUploadDTO;
import com.senai.pousadabackend.domain.imagem.quarto.service.ImagemQuartoService;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoService;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.infraestructure.imagem.MinioDeleteClient;
import com.senai.pousadabackend.infraestructure.imagem.MinioUploadClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        service = new ImagemQuartoService(quartoService, minioDeleteClient, minioUploadClient, repository);
        mockFactory = new MockFactory();
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

    @Nested
    class Dado_um_quarto_existente {

        private Quarto quarto;

        @BeforeEach
        void setUp() {
            quarto = mockFactory.quartoPadrao();
        }

        @Nested
        class Quando_fazer_upload_de_imagem_valida {

            @BeforeEach
            void setUp() {
                when(quartoService.buscarPorId(1L)).thenReturn(quarto);
                when(minioUploadClient.uploadImagem(any(MultipartFile.class)))
                        .thenReturn(mockFactory.resultadoUploadDTO());
            }

            @Test
            void Entao_deve_salvar_a_imagem() {
                service.uploadImagem(List.of(imagemValida()), 1L);

                assertThatCode(() -> service.uploadImagem(List.of(imagemValida()), 1L)).doesNotThrowAnyException();
            }
        }

        @Nested
        class Quando_fazer_upload_de_multiplas_imagens {

            @BeforeEach
            void setUp() {
                when(quartoService.buscarPorId(1L)).thenReturn(quarto);
                when(minioUploadClient.uploadImagem(any(MultipartFile.class)))
                        .thenReturn(mockFactory.resultadoUploadDTO())
                        .thenReturn(ResultadoUploadDTO.builder().objectName("file-id-2").url("http://url2").build());
            }

            @Test
            void Entao_deve_salvar_todas_as_imagens() {
                service.uploadImagem(List.of(imagemValida(), imagemValida()), 1L);

                assertThatCode(() -> service.uploadImagem(List.of(imagemValida(), imagemValida()), 1L)).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_uma_imagem_muito_grande {

        @Nested
        class Quando_fazer_upload {

            @Test
            void Entao_deve_informar_que_o_tamanho_excede_o_limite() {
                assertThatThrownBy(() -> service.uploadImagem(List.of(imagemGrande()), 1L))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("5MB");
            }
        }
    }

    @Nested
    class Dado_um_id_de_quarto {

        @Nested
        class Quando_listar_imagens {

            @BeforeEach
            void setUp() {
                when(repository.listarPor(1L)).thenReturn(List.of(mockFactory.imagemQuartoPadrao()));
            }

            @Test
            void Entao_deve_retornar_as_imagens_do_quarto() {
                List<ImagemQuarto> resultado = service.listarPor(1L);

                assertThat(resultado).hasSize(1);
            }
        }
    }

    @Nested
    class Dado_uma_imagem_com_file_id_e_url {

        private ImagemQuarto imagem;

        @BeforeEach
        void setUp() {
            imagem = mockFactory.imagemQuartoPadrao();
        }

        @Nested
        class Quando_deletar {

            @Test
            void Entao_deve_remover_do_minio_e_do_banco() {
                service.deletar(imagem);

                assertThatCode(() -> service.deletar(imagem)).doesNotThrowAnyException();
            }
        }
    }

    @Nested
    class Dado_uma_imagem_sem_file_id {

        private ImagemQuarto imagem;

        @BeforeEach
        void setUp() {
            imagem = mockFactory.imagemQuartoSemFileId();
        }

        @Nested
        class Quando_deletar {

            @Test
            void Entao_deve_informar_que_o_id_do_arquivo_e_obrigatorio() {
                assertThatThrownBy(() -> service.deletar(imagem))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("id do arquivo");
            }
        }
    }

    @Nested
    class Dado_uma_imagem_sem_url {

        private ImagemQuarto imagem;

        @BeforeEach
        void setUp() {
            imagem = mockFactory.imagemQuartoSemUrl();
        }

        @Nested
        class Quando_deletar {

            @Test
            void Entao_deve_informar_que_a_url_e_obrigatoria() {
                assertThatThrownBy(() -> service.deletar(imagem))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("url");
            }
        }
    }
}
