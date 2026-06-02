package com.senai.pousadabackend.domain.imagem.quarto;

import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.imagem.quarto.dto.ImagemQuartoDTO;
import com.senai.pousadabackend.domain.imagem.quarto.service.ImagemQuartoService;
import com.senai.pousadabackend.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(ImagemQuartoController.class)
class ImagemQuartoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private ImagemQuartoService service;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private ImagemQuartoMapper mapper;

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        mockFactory = new MockFactory();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    private MockMultipartFile imagemValida() {
        return new MockMultipartFile(
                "imagens", "foto.jpg", "image/jpeg", new byte[1024]
        );
    }

    @Nested
    class Dado_uma_requisicao_sem_autenticacao {

        @Nested
        class Quando_fazer_upload {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(multipart("/imagens/room")
                                .file(imagemValida())
                                .param("idQuarto", "1")
                                .with(csrf()))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_operacao {

        @Nested
        class Quando_fazer_upload_de_imagem_valida {

            @Test
            void Entao_deve_fazer_o_upload_com_sucesso() throws Exception {
                doNothing().when(service).uploadImagem(anyList(), anyLong());
                mockMvc.perform(multipart("/imagens/room")
                                .file(imagemValida())
                                .param("idQuarto", "1")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_imagem-operacao"))))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        class Quando_listar_imagens_por_quarto {

            @BeforeEach
            void setUp() {
                when(service.listarPor(1L)).thenReturn(List.of(mockFactory.imagemQuartoPadrao()));
                when(mapper.toDTO(any(ImagemQuarto.class))).thenReturn(ImagemQuartoDTO.builder().build());
            }

            @Test
            void Entao_deve_retornar_as_imagens() throws Exception {
                mockMvc.perform(get("/imagens/1")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_imagem-operacao"))))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        class Quando_deletar_imagem {

            @BeforeEach
            void setUp() {
                when(mapper.toEntity(any(ImagemQuartoDTO.class))).thenReturn(mockFactory.imagemQuartoPadrao());
            }

            @Test
            void Entao_deve_remover_a_imagem() throws Exception {
                ImagemQuartoDTO dto = ImagemQuartoDTO.builder()
                        .id(1L)
                        .fileId("file-id-123")
                        .url("http://url")
                        .build();

                mockMvc.perform(delete("/imagens")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(dto))
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_imagem-operacao"))))
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    class Dado_uma_imagem_muito_grande {

        private MockMultipartFile imagemGrande() {
            return new MockMultipartFile(
                    "imagens", "foto.jpg", "image/jpeg", new byte[6 * 1024 * 1024]
            );
        }

        @Nested
        class Quando_fazer_upload {

            @Test
            void Entao_deve_informar_que_o_tamanho_excede_o_limite() throws Exception {
                doThrow(new BusinessException("O arquivo excede o limite de 5MB.")).when(service).uploadImagem(anyList(), anyLong());
                mockMvc.perform(multipart("/imagens/room")
                                .file(imagemGrande())
                                .param("idQuarto", "1")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_imagem-operacao"))))
                        .andExpect(status().isBadRequest());
            }
        }
    }
}
