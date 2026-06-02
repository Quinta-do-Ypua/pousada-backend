package com.senai.pousadabackend.domain.resumo;

import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.resumo.dto.ResumoReservaDto;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResumoReservaController.class)
class ResumoReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResumoReservaService resumoReservaService;

    @MockitoBean
    private ResumoReservaMapper resumoReservaMapper;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        mockFactory = new MockFactory();
    }

    @Nested
    class Dado_uma_requisicao_sem_autenticacao {

        @Nested
        class Quando_buscar_por_id {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(get("/notas-fiscais/1"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_um_id_existente {

        private Long idExistente = 1L;

        @Nested
        class Quando_buscar_por_id {

            @BeforeEach
            void setUp() {
                when(resumoReservaService.buscarPorId(idExistente)).thenReturn(mockFactory.resumoReservaExistente());
                when(resumoReservaMapper.toDTO(any())).thenReturn(mockFactory.resumoReservaDto());
            }

            @Test
            void Entao_deve_retornar_o_resumo() throws Exception {
                mockMvc.perform(get("/notas-fiscais/" + idExistente)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.idNotaFiscal").value(1))
                        .andExpect(jsonPath("$.numero").value("NF-123456"));
            }
        }
    }

    @Nested
    class Dado_um_id_inexistente {

        private static final Long ID_INEXISTENTE = 99L;

        @Nested
        class Quando_buscar_por_id {

            @BeforeEach
            void setUp() {
                when(resumoReservaService.buscarPorId(ID_INEXISTENTE))
                        .thenThrow(new RegistroNaoEncontradoException("Resumo não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(get("/notas-fiscais/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }
    }
}