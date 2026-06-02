package com.senai.pousadabackend.domain.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.cliente.dto.ClienteDTO;
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

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private ClienteMapper mapper;

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
        class Quando_listar_clientes {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(get("/clientes"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_visualizacao {

        @Nested
        class Quando_listar_clientes {

            @BeforeEach
            void setUp() {
                when(clienteService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.clientePadrao())));
                when(mapper.toDTO(any(Cliente.class))).thenReturn(mockFactory.clienteDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_clientes() throws Exception {
                mockMvc.perform(get("/clientes")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_cliente-visualizacao"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Maria"))
                        .andExpect(jsonPath("$.content[0].email").value("maria@email.com"));
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_admin {

        @Nested
        class Quando_listar_clientes {

            @BeforeEach
            void setUp() {
                when(clienteService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.clientePadrao())));
                when(mapper.toDTO(any(Cliente.class))).thenReturn(mockFactory.clienteDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_clientes() throws Exception {
                mockMvc.perform(get("/clientes")
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
                when(clienteService.buscarPorId(idExistente)).thenReturn(mockFactory.clientePadrao());
                when(mapper.toDTO(any(Cliente.class))).thenReturn(mockFactory.clienteDTO());
            }

            @Test
            void Entao_deve_retornar_o_cliente() throws Exception {
                mockMvc.perform(get("/clientes/" + idExistente)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.nome").value("Maria"));
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(clienteService.excluir(idExistente)).thenReturn(mockFactory.clientePadrao());
                when(mapper.toDTO(any(Cliente.class))).thenReturn(mockFactory.clienteDTO());
            }

            @Test
            void Entao_deve_remover_o_cliente() throws Exception {
                mockMvc.perform(delete("/clientes/" + idExistente)
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
                when(clienteService.buscarPorId(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Cliente não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(get("/clientes/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(clienteService.excluir(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Cliente não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(delete("/clientes/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Dado_um_cliente_valido {

        private ClienteDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.clienteDTOComNome("João");
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @BeforeEach
            void setUp() {
                Cliente novo = mockFactory.clienteComIdENome(2L, "João");
                when(mapper.toEntity(any(ClienteDTO.class))).thenReturn(novo);
                when(clienteService.salvar(any(Cliente.class))).thenReturn(novo);
                when(mapper.toDTO(any(Cliente.class))).thenReturn(mockFactory.clienteDTOComNome("João"));
            }

            @Test
            void Entao_deve_criar_o_cliente() throws Exception {
                mockMvc.perform(post("/clientes")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_cliente-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.nome").value("João"));
            }
        }
    }

    @Nested
    class Dado_um_cliente_sem_nome {

        private ClienteDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.clienteDTOSemNome();
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @Test
            void Entao_deve_informar_que_o_nome_e_obrigatorio() throws Exception {
                mockMvc.perform(post("/clientes")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_cliente-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    class Dado_um_cliente_existente {

        private ClienteDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.clienteDTO();
        }

        @Nested
        class Quando_atualizar_com_role_operacao {

            @BeforeEach
            void setUp() {
                when(mapper.toEntity(any(ClienteDTO.class))).thenReturn(mockFactory.clientePadrao());
                when(clienteService.salvar(any(Cliente.class))).thenReturn(mockFactory.clientePadrao());
                when(mapper.toDTO(any(Cliente.class))).thenReturn(dto);
            }

            @Test
            void Entao_deve_atualizar_o_cliente() throws Exception {
                mockMvc.perform(put("/clientes")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_cliente-operacao")))
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
                when(clienteService.buscarPorSpecification(eq("nome==Maria"), any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.clientePadrao())));
                when(mapper.toDTO(any(Cliente.class))).thenReturn(mockFactory.clienteDTO());
            }

            @Test
            void Entao_deve_retornar_clientes_filtrados() throws Exception {
                mockMvc.perform(get("/clientes")
                                .param("search", "nome==Maria")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Maria"));
            }
        }
    }
}
