package com.senai.pousadabackend.domain.endereco;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.endereco.dto.EnderecoDTO;
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

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnderecoService enderecoService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private EnderecoMapper mapper;

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
        class Quando_listar_enderecos {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(get("/enderecos"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_visualizacao {

        @Nested
        class Quando_listar_enderecos {

            @BeforeEach
            void setUp() {
                when(enderecoService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.enderecoExistente())));
                when(mapper.toDTO(any(Endereco.class))).thenReturn(mockFactory.enderecoDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_enderecos() throws Exception {
                mockMvc.perform(get("/enderecos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_endereco-visualizacao"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].cidade").value("São Paulo"))
                        .andExpect(jsonPath("$.content[0].estado").value("SP"));
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_admin {

        @Nested
        class Quando_listar_enderecos {

            @BeforeEach
            void setUp() {
                when(enderecoService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.enderecoExistente())));
                when(mapper.toDTO(any(Endereco.class))).thenReturn(mockFactory.enderecoDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_enderecos() throws Exception {
                mockMvc.perform(get("/enderecos")
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
                when(enderecoService.buscarPorId(idExistente)).thenReturn(mockFactory.enderecoExistente());
                when(mapper.toDTO(any(Endereco.class))).thenReturn(mockFactory.enderecoDTO());
            }

            @Test
            void Entao_deve_retornar_o_endereco() throws Exception {
                mockMvc.perform(get("/enderecos/" + idExistente)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.cidade").value("São Paulo"));
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(enderecoService.excluir(idExistente)).thenReturn(mockFactory.enderecoExistente());
                when(mapper.toDTO(any(Endereco.class))).thenReturn(mockFactory.enderecoDTO());
            }

            @Test
            void Entao_deve_remover_o_endereco() throws Exception {
                mockMvc.perform(delete("/enderecos/" + idExistente)
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
                when(enderecoService.buscarPorId(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Endereço não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(get("/enderecos/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(enderecoService.excluir(ID_INEXISTENTE))
                        .thenThrow(new com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException("Endereço não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(delete("/enderecos/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Dado_um_endereco_valido {

        private EnderecoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.enderecoDTOComCidade("Rio de Janeiro");
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @BeforeEach
            void setUp() {
                Endereco novo = mockFactory.enderecoComIdECidade(2L, "Rio de Janeiro");
                when(mapper.toEntity(any(EnderecoDTO.class))).thenReturn(novo);
                when(enderecoService.salvar(any(Endereco.class))).thenReturn(novo);
                when(mapper.toDTO(any(Endereco.class))).thenReturn(mockFactory.enderecoDTOComCidade("Rio de Janeiro"));
            }

            @Test
            void Entao_deve_criar_o_endereco() throws Exception {
                mockMvc.perform(post("/enderecos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_endereco-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.cidade").value("Rio de Janeiro"));
            }
        }
    }

    @Nested
    class Dado_um_endereco_sem_cidade {

        private EnderecoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.enderecoDTOSemCidade();
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @Test
            void Entao_deve_informar_que_a_cidade_e_obrigatoria() throws Exception {
                mockMvc.perform(post("/enderecos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_endereco-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    class Dado_um_endereco_existente {

        private EnderecoDTO dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.enderecoDTO();
        }

        @Nested
        class Quando_atualizar_com_role_operacao {

            @BeforeEach
            void setUp() {
                when(mapper.toEntity(any(EnderecoDTO.class))).thenReturn(mockFactory.enderecoExistente());
                when(enderecoService.salvar(any(Endereco.class))).thenReturn(mockFactory.enderecoExistente());
                when(mapper.toDTO(any(Endereco.class))).thenReturn(dto);
            }

            @Test
            void Entao_deve_atualizar_o_endereco() throws Exception {
                mockMvc.perform(put("/enderecos")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_endereco-operacao")))
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
                when(enderecoService.buscarPorSpecification(eq("cidade==São Paulo"), any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.enderecoExistente())));
                when(mapper.toDTO(any(Endereco.class))).thenReturn(mockFactory.enderecoDTO());
            }

            @Test
            void Entao_deve_retornar_enderecos_filtrados() throws Exception {
                mockMvc.perform(get("/enderecos")
                                .param("search", "cidade==São Paulo")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].cidade").value("São Paulo"));
            }
        }
    }
}
