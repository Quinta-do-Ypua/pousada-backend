package com.senai.pousadabackend.domain.meioPagamento;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.core.base.BaseServiceInterface;
import com.senai.pousadabackend.domain.meioPagamento.dto.MeioPagamentoDTO;
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

@WebMvcTest(MeioPagamentoController.class)
class MeioPagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BaseServiceInterface<MeioPagamento, Long> baseServiceInterface;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private MeioPagamentoMapper mapper;

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
        class Quando_listar_meios_pagamento {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(get("/meios-pagamento"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_visualizacao {

        @Nested
        class Quando_listar_meios_pagamento {

            @BeforeEach
            void setUp() {
                when(baseServiceInterface.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.meioPagamentoExistente())));
                when(mapper.toDTO(any(MeioPagamento.class))).thenReturn(mockFactory.meioPagamentoDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_meios_pagamento() throws Exception {
                mockMvc.perform(get("/meios-pagamento")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_meio-pagamento-visualizacao"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Cartão de Crédito"))
                        .andExpect(jsonPath("$.content[0].tipo").value("CREDITO"));
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_admin {

        @Nested
        class Quando_listar_meios_pagamento {

            @BeforeEach
            void setUp() {
                when(baseServiceInterface.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.meioPagamentoExistente())));
                when(mapper.toDTO(any(MeioPagamento.class))).thenReturn(mockFactory.meioPagamentoDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_meios_pagamento() throws Exception {
                mockMvc.perform(get("/meios-pagamento")
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
                when(baseServiceInterface.buscarPorId(idExistente)).thenReturn(mockFactory.meioPagamentoExistente());
                when(mapper.toDTO(any(MeioPagamento.class))).thenReturn(mockFactory.meioPagamentoDTO());
            }

            @Test
            void Entao_deve_retornar_o_meio_pagamento() throws Exception {
                mockMvc.perform(get("/meios-pagamento/" + idExistente)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.nome").value("Cartão de Crédito"));
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(baseServiceInterface.excluir(idExistente)).thenReturn(mockFactory.meioPagamentoExistente());
                when(mapper.toDTO(any(MeioPagamento.class))).thenReturn(mockFactory.meioPagamentoDTO());
            }

            @Test
            void Entao_deve_remover_o_meio_pagamento() throws Exception {
                mockMvc.perform(delete("/meios-pagamento/" + idExistente)
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
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Meio de pagamento não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(get("/meios-pagamento/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(baseServiceInterface.excluir(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Meio de pagamento não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(delete("/meios-pagamento/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Dado_um_meio_pagamento_valido {

        private MeioPagamentoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.meioPagamentoDTOComNome("PIX");
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @BeforeEach
            void setUp() {
                MeioPagamento novo = mockFactory.meioPagamentoComIdENome(2L, "PIX");
                when(mapper.toEntity(any(MeioPagamentoDTO.class))).thenReturn(novo);
                when(baseServiceInterface.salvar(any(MeioPagamento.class))).thenReturn(novo);
                when(mapper.toDTO(any(MeioPagamento.class))).thenReturn(mockFactory.meioPagamentoDTOComNome("PIX"));
            }

            @Test
            void Entao_deve_criar_o_meio_pagamento() throws Exception {
                mockMvc.perform(post("/meios-pagamento")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_meio-pagamento-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.nome").value("PIX"));
            }
        }
    }

    @Nested
    class Dado_um_meio_pagamento_sem_nome {

        private MeioPagamentoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.meioPagamentoDTOSemNome();
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @Test
            void Entao_deve_informar_que_o_nome_e_obrigatorio() throws Exception {
                mockMvc.perform(post("/meios-pagamento")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_meio-pagamento-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    class Dado_um_meio_pagamento_existente {

        private MeioPagamentoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.meioPagamentoDTO();
        }

        @Nested
        class Quando_atualizar_com_role_operacao {

            @BeforeEach
            void setUp() {
                when(mapper.toEntity(any(MeioPagamentoDTO.class))).thenReturn(mockFactory.meioPagamentoExistente());
                when(baseServiceInterface.salvar(any(MeioPagamento.class))).thenReturn(mockFactory.meioPagamentoExistente());
                when(mapper.toDTO(any(MeioPagamento.class))).thenReturn(dto);
            }

            @Test
            void Entao_deve_atualizar_o_meio_pagamento() throws Exception {
                mockMvc.perform(put("/meios-pagamento")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_meio-pagamento-operacao")))
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
                when(baseServiceInterface.buscarPorSpecification(eq("nome==Cartão de Crédito"), any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.meioPagamentoExistente())));
                when(mapper.toDTO(any(MeioPagamento.class))).thenReturn(mockFactory.meioPagamentoDTO());
            }

            @Test
            void Entao_deve_retornar_meios_pagamento_filtrados() throws Exception {
                mockMvc.perform(get("/meios-pagamento")
                                .param("search", "nome==Cartão de Crédito")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Cartão de Crédito"));
            }
        }
    }
}