package com.senai.pousadabackend.domain.cupom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.cupom.dto.CupomDTO;
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

@WebMvcTest(CupomController.class)
class CupomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CupomService cupomService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private CupomMapper mapper;

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
        class Quando_listar_cupons {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(get("/cupons"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_visualizacao {

        @Nested
        class Quando_listar_cupons {

            @BeforeEach
            void setUp() {
                when(cupomService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.cupomExistente())));
                when(mapper.toDTO(any(Cupom.class))).thenReturn(mockFactory.cupomDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_cupons() throws Exception {
                mockMvc.perform(get("/cupons")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_cupom-visualizacao"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Desconto 10%"))
                        .andExpect(jsonPath("$.content[0].codigo").value("DESC10"));
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_admin {

        @Nested
        class Quando_listar_cupons {

            @BeforeEach
            void setUp() {
                when(cupomService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.cupomExistente())));
                when(mapper.toDTO(any(Cupom.class))).thenReturn(mockFactory.cupomDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_cupons() throws Exception {
                mockMvc.perform(get("/cupons")
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
                when(cupomService.buscarPorId(idExistente)).thenReturn(mockFactory.cupomExistente());
                when(mapper.toDTO(any(Cupom.class))).thenReturn(mockFactory.cupomDTO());
            }

            @Test
            void Entao_deve_retornar_o_cupom() throws Exception {
                mockMvc.perform(get("/cupons/" + idExistente)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.nome").value("Desconto 10%"));
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(cupomService.excluir(idExistente)).thenReturn(mockFactory.cupomExistente());
                when(mapper.toDTO(any(Cupom.class))).thenReturn(mockFactory.cupomDTO());
            }

            @Test
            void Entao_deve_remover_o_cupom() throws Exception {
                mockMvc.perform(delete("/cupons/" + idExistente)
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
                when(cupomService.buscarPorId(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Cupom não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(get("/cupons/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(cupomService.excluir(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Cupom não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(delete("/cupons/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Dado_um_cupom_valido {

        private CupomDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.cupomDTOComNome("Desconto 20%");
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @BeforeEach
            void setUp() {
                Cupom novo = mockFactory.cupomComIdENome(2L, "Desconto 20%");
                when(mapper.toEntity(any(CupomDTO.class))).thenReturn(novo);
                when(cupomService.salvar(any(Cupom.class))).thenReturn(novo);
                when(mapper.toDTO(any(Cupom.class))).thenReturn(mockFactory.cupomDTOComNome("Desconto 20%"));
            }

            @Test
            void Entao_deve_criar_o_cupom() throws Exception {
                mockMvc.perform(post("/cupons")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_cupom-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.nome").value("Desconto 20%"));
            }
        }
    }

    @Nested
    class Dado_um_cupom_sem_nome {

        private CupomDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.cupomDTOSemNome();
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @Test
            void Entao_deve_informar_que_o_nome_e_obrigatorio() throws Exception {
                mockMvc.perform(post("/cupons")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_cupom-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    class Dado_um_cupom_existente {

        private CupomDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.cupomDTO();
        }

        @Nested
        class Quando_atualizar_com_role_operacao {

            @BeforeEach
            void setUp() {
                when(mapper.toEntity(any(CupomDTO.class))).thenReturn(mockFactory.cupomExistente());
                when(cupomService.salvar(any(Cupom.class))).thenReturn(mockFactory.cupomExistente());
                when(mapper.toDTO(any(Cupom.class))).thenReturn(dto);
            }

            @Test
            void Entao_deve_atualizar_o_cupom() throws Exception {
                mockMvc.perform(put("/cupons")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_cupom-operacao")))
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
                when(cupomService.buscarPorSpecification(eq("nome==Desconto 10%"), any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.cupomExistente())));
                when(mapper.toDTO(any(Cupom.class))).thenReturn(mockFactory.cupomDTO());
            }

            @Test
            void Entao_deve_retornar_cupons_filtrados() throws Exception {
                mockMvc.perform(get("/cupons")
                                .param("search", "nome==Desconto 10%")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Desconto 10%"));
            }
        }
    }
}
