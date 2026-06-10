package com.senai.pousadabackend.domain.parametro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.core.base.BaseServiceInterface;
import com.senai.pousadabackend.domain.parametro.dto.ParametroReservaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParametroReservaController.class)
class ParametroReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BaseServiceInterface<ParametroReserva, Long> baseServiceInterface;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private ParametroReservaMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private MockFactory mockFactory;

    @BeforeEach
    void setUp() {
        mockFactory = new MockFactory();
    }

    @Nested
    class Dado_uma_requisicao_sem_autenticacao {

        @Nested
        class Quando_listar_parametros {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(get("/api/parametros-reserva"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_visualizacao {

        @Nested
        class Quando_listar_parametros {

            @BeforeEach
            void setUp() {
                when(baseServiceInterface.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.parametroReservaExistente())));
                when(mapper.toDTO(any(ParametroReserva.class))).thenReturn(mockFactory.parametroReservaDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_parametros() throws Exception {
                mockMvc.perform(get("/api/parametros-reserva")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_parametro-reserva-visualizacao"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].bloquearReservaComPendencia").value(true))
                        .andExpect(jsonPath("$.content[0].maxReservasAtivasPorUsuario").value(3));
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_admin {

        @Nested
        class Quando_listar_parametros {

            @BeforeEach
            void setUp() {
                when(baseServiceInterface.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.parametroReservaExistente())));
                when(mapper.toDTO(any(ParametroReserva.class))).thenReturn(mockFactory.parametroReservaDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_parametros() throws Exception {
                mockMvc.perform(get("/api/parametros-reserva")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk());
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
                when(baseServiceInterface.buscarPorId(idExistente)).thenReturn(mockFactory.parametroReservaExistente());
                when(mapper.toDTO(any(ParametroReserva.class))).thenReturn(mockFactory.parametroReservaDTO());
            }

            @Test
            void Entao_deve_retornar_o_parametro() throws Exception {
                mockMvc.perform(get("/api/parametros-reserva/" + idExistente)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.maxReservasAtivasPorUsuario").value(3));
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(baseServiceInterface.buscarPorId(idExistente)).thenReturn(mockFactory.parametroReservaExistente());
                when(baseServiceInterface.excluir(idExistente)).thenReturn(mockFactory.parametroReservaExistente());
                when(mapper.toDTO(any(ParametroReserva.class))).thenReturn(mockFactory.parametroReservaDTO());
            }

            @Test
            void Entao_deve_remover_o_parametro() throws Exception {
                mockMvc.perform(delete("/api/parametros-reserva/" + idExistente)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1));
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
                when(baseServiceInterface.buscarPorId(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Parâmetro não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(get("/api/parametros-reserva/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(baseServiceInterface.excluir(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Parâmetro não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(delete("/api/parametros-reserva/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Dado_um_parametro_valido {

        private ParametroReservaDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.parametroReservaDTOSemId();
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @BeforeEach
            void setUp() {
                ParametroReserva novo = mockFactory.parametroReservaComId(2L);
                when(mapper.toEntity(any(ParametroReservaDTO.class))).thenReturn(novo);
                when(baseServiceInterface.salvar(any(ParametroReserva.class))).thenReturn(novo);
                when(mapper.toDTO(any(ParametroReserva.class))).thenReturn(mockFactory.parametroReservaDTO());
            }

            @Test
            void Entao_deve_criar_o_parametro() throws Exception {
                mockMvc.perform(post("/api/parametros-reserva")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_parametro-reserva-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.maxReservasAtivasPorUsuario").value(3));
            }
        }
    }

    @Nested
    class Dado_um_parametro_existente {

        private ParametroReservaDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.parametroReservaDTO();
        }

        @Nested
        class Quando_atualizar_com_role_operacao {

            @BeforeEach
            void setUp() {
                when(mapper.toEntity(any(ParametroReservaDTO.class))).thenReturn(mockFactory.parametroReservaExistente());
                when(baseServiceInterface.salvar(any(ParametroReserva.class))).thenReturn(mockFactory.parametroReservaExistente());
                when(mapper.toDTO(any(ParametroReserva.class))).thenReturn(dto);
            }

            @Test
            void Entao_deve_atualizar_o_parametro() throws Exception {
                mockMvc.perform(put("/api/parametros-reserva")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_parametro-reserva-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    class Dado_uma_busca_com_filtro {

        @Nested
        class Quando_buscar_por_specification {

            @BeforeEach
            void setUp() {
                when(baseServiceInterface.buscarPorSpecification(eq("bloquearReservaComPendencia==true"), any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.parametroReservaExistente())));
                when(mapper.toDTO(any(ParametroReserva.class))).thenReturn(mockFactory.parametroReservaDTO());
            }

            @Test
            void Entao_deve_retornar_parametros_filtrados() throws Exception {
                mockMvc.perform(get("/api/parametros-reserva")
                                .param("search", "bloquearReservaComPendencia==true")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].bloquearReservaComPendencia").value(true));
            }
        }
    }
}