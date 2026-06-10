package com.senai.pousadabackend.domain.quarto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.quarto.dto.QuartoDTO;
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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuartoController.class)
class QuartoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuartoService quartoService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private QuartoMapper mapper;

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
        class Quando_listar_quartos {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(get("/quartos"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_visualizacao {

        @Nested
        class Quando_listar_quartos {

            @BeforeEach
            void setUp() {
                when(quartoService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.quartoExistente())));
                when(mapper.toDTO(any(Quarto.class))).thenReturn(mockFactory.quartoDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_quartos() throws Exception {
                mockMvc.perform(get("/quartos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_quarto-visualizacao"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Suite 01"))
                        .andExpect(jsonPath("$.content[0].capacidade").value(2));
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_admin {

        @Nested
        class Quando_listar_quartos {

            @BeforeEach
            void setUp() {
                when(quartoService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.quartoExistente())));
                when(mapper.toDTO(any(Quarto.class))).thenReturn(mockFactory.quartoDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_quartos() throws Exception {
                mockMvc.perform(get("/quartos")
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
                when(quartoService.buscarPorId(idExistente)).thenReturn(mockFactory.quartoExistente());
                when(mapper.toDTO(any(Quarto.class))).thenReturn(mockFactory.quartoDTO());
            }

            @Test
            void Entao_deve_retornar_o_quarto() throws Exception {
                mockMvc.perform(get("/quartos/" + idExistente)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.nome").value("Suite 01"));
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(quartoService.buscarPorId(idExistente)).thenReturn(mockFactory.quartoExistente());
                when(quartoService.excluir(idExistente)).thenReturn(mockFactory.quartoExistente());
                when(mapper.toDTO(any(Quarto.class))).thenReturn(mockFactory.quartoDTO());
            }

            @Test
            void Entao_deve_remover_o_quarto() throws Exception {
                mockMvc.perform(delete("/quartos/" + idExistente)
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
                when(quartoService.buscarPorId(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Quarto não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(get("/quartos/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(quartoService.excluir(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Quarto não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(delete("/quartos/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Dado_um_quarto_valido {

        private QuartoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.quartoDTOComNome("Suite 02");
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @BeforeEach
            void setUp() {
                Quarto novo = mockFactory.quartoComIdENome(2L, "Suite 02");
                when(mapper.toEntity(any(QuartoDTO.class))).thenReturn(novo);
                when(quartoService.salvar(any(Quarto.class))).thenReturn(novo);
                when(mapper.toDTO(any(Quarto.class))).thenReturn(mockFactory.quartoDTOComNome("Suite 02"));
            }

            @Test
            void Entao_deve_criar_o_quarto() throws Exception {
                mockMvc.perform(post("/quartos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_quarto-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.nome").value("Suite 02"));
            }
        }
    }

    @Nested
    class Dado_um_quarto_sem_nome {

        private QuartoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.quartoDTOSemNome();
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @Test
            void Entao_deve_informar_que_o_nome_e_obrigatorio() throws Exception {
                mockMvc.perform(post("/quartos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_quarto-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    class Dado_um_quarto_existente {

        private QuartoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.quartoDTO();
        }

        @Nested
        class Quando_atualizar_com_role_operacao {

            @BeforeEach
            void setUp() {
                when(mapper.toEntity(any(QuartoDTO.class))).thenReturn(mockFactory.quartoExistente());
                when(quartoService.salvar(any(Quarto.class))).thenReturn(mockFactory.quartoExistente());
                when(mapper.toDTO(any(Quarto.class))).thenReturn(dto);
            }

            @Test
            void Entao_deve_atualizar_o_quarto() throws Exception {
                mockMvc.perform(put("/quartos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_quarto-operacao")))
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
                when(quartoService.buscarPorSpecification(eq("nome==Suite 01"), any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.quartoExistente())));
                when(mapper.toDTO(any(Quarto.class))).thenReturn(mockFactory.quartoDTO());
            }

            @Test
            void Entao_deve_retornar_quartos_filtrados() throws Exception {
                mockMvc.perform(get("/quartos")
                                .param("search", "nome==Suite 01")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Suite 01"));
            }
        }
    }
}
