package com.senai.pousadabackend.domain.complemento;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.complemento.dto.ComplementoDTO;
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

@WebMvcTest(ComplementoController.class)
class ComplementoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ComplementoService complementoService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private ComplementoMapper mapper;

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
        class Quando_listar_complementos {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(get("/complementos"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_visualizacao {

        @Nested
        class Quando_listar_complementos {

            @BeforeEach
            void setUp() {
                when(complementoService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.complementoExistente())));
                when(mapper.toDTO(any(Complemento.class))).thenReturn(mockFactory.complementoDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_complementos() throws Exception {
                mockMvc.perform(get("/complementos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_complemento-visualizacao"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Café da manhã"))
                        .andExpect(jsonPath("$.content[0].valor").value(50.00));
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_admin {

        @Nested
        class Quando_listar_complementos {

            @BeforeEach
            void setUp() {
                when(complementoService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.complementoExistente())));
                when(mapper.toDTO(any(Complemento.class))).thenReturn(mockFactory.complementoDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_complementos() throws Exception {
                mockMvc.perform(get("/complementos")
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
                when(complementoService.buscarPorId(idExistente)).thenReturn(mockFactory.complementoExistente());
                when(mapper.toDTO(any(Complemento.class))).thenReturn(mockFactory.complementoDTO());
            }

            @Test
            void Entao_deve_retornar_o_complemento() throws Exception {
                mockMvc.perform(get("/complementos/" + idExistente)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.nome").value("Café da manhã"));
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(complementoService.excluir(idExistente)).thenReturn(mockFactory.complementoExistente());
                when(mapper.toDTO(any(Complemento.class))).thenReturn(mockFactory.complementoDTO());
            }

            @Test
            void Entao_deve_remover_o_complemento() throws Exception {
                mockMvc.perform(delete("/complementos/" + idExistente)
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
                when(complementoService.buscarPorId(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Complemento não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(get("/complementos/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(complementoService.excluir(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Complemento não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(delete("/complementos/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Dado_um_complemento_valido {

        private ComplementoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.complementoDTOComNome("Almoço");
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @BeforeEach
            void setUp() {
                Complemento novo = mockFactory.complementoComIdENome(2L, "Almoço");
                when(mapper.toEntity(any(ComplementoDTO.class))).thenReturn(novo);
                when(complementoService.salvar(any(Complemento.class))).thenReturn(novo);
                when(mapper.toDTO(any(Complemento.class))).thenReturn(mockFactory.complementoDTOComNome("Almoço"));
            }

            @Test
            void Entao_deve_criar_o_complemento() throws Exception {
                mockMvc.perform(post("/complementos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_complemento-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.nome").value("Almoço"));
            }
        }
    }

    @Nested
    class Dado_um_complemento_sem_nome {

        private ComplementoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.complementoDTOSemNome();
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @Test
            void Entao_deve_informar_que_o_nome_e_obrigatorio() throws Exception {
                mockMvc.perform(post("/complementos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_complemento-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    class Dado_um_complemento_existente {

        private ComplementoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.complementoDTO();
        }

        @Nested
        class Quando_atualizar_com_role_operacao {

            @BeforeEach
            void setUp() {
                when(mapper.toEntity(any(ComplementoDTO.class))).thenReturn(mockFactory.complementoExistente());
                when(complementoService.salvar(any(Complemento.class))).thenReturn(mockFactory.complementoExistente());
                when(mapper.toDTO(any(Complemento.class))).thenReturn(dto);
            }

            @Test
            void Entao_deve_atualizar_o_complemento() throws Exception {
                mockMvc.perform(put("/complementos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_complemento-operacao")))
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
                when(complementoService.buscarPorSpecification(eq("nome==Café da manhã"), any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.complementoExistente())));
                when(mapper.toDTO(any(Complemento.class))).thenReturn(mockFactory.complementoDTO());
            }

            @Test
            void Entao_deve_retornar_complementos_filtrados() throws Exception {
                mockMvc.perform(get("/complementos")
                                .param("search", "nome==Café da manhã")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Café da manhã"));
            }
        }
    }
}