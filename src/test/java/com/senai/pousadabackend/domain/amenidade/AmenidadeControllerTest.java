package com.senai.pousadabackend.domain.amenidade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.domain.amenidade.dto.AmenidadeDto;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AmenidadeController.class)
@Import(AmenidadeMapper.class)
class AmenidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AmenidadeService amenidadeService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private AmenidadeMapper mapper;

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
        class Quando_listar_amenidades {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(get("/amenidades"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_visualizacao {

        @Nested
        class Quando_listar_amenidades {

            @BeforeEach
            void setUp() {
                when(amenidadeService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.amenidadeExistente())));
                when(mapper.toDTO(any(Amenidade.class))).thenReturn(mockFactory.amenidadeDto());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_amenidades() throws Exception {
                mockMvc.perform(get("/amenidades")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_amenidade-visualizacao"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Piscina"))
                        .andExpect(jsonPath("$.content[0].icone").value("pool"));
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_admin {

        @Nested
        class Quando_listar_amenidades {

            @BeforeEach
            void setUp() {
                when(amenidadeService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.amenidadeExistente())));
                when(mapper.toDTO(any(Amenidade.class))).thenReturn(mockFactory.amenidadeDto());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_amenidades() throws Exception {
                mockMvc.perform(get("/amenidades")
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
                when(amenidadeService.buscarPorId(idExistente)).thenReturn(mockFactory.amenidadeExistente());
                when(mapper.toDTO(any(Amenidade.class))).thenReturn(mockFactory.amenidadeDto());
            }

            @Test
            void Entao_deve_retornar_a_amenidade() throws Exception {
                mockMvc.perform(get("/amenidades/" + idExistente)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.nome").value("Piscina"));
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(amenidadeService.excluir(idExistente)).thenReturn(mockFactory.amenidadeExistente());
                when(mapper.toDTO(any(Amenidade.class))).thenReturn(mockFactory.amenidadeDto());
            }

            @Test
            void Entao_deve_remover_a_amenidade() throws Exception {
                mockMvc.perform(delete("/amenidades/" + idExistente)
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
                when(amenidadeService.buscarPorId(ID_INEXISTENTE))
                        .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(get("/amenidades/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.mensagem").value("Não encontrado"));
            }
        }

        @Nested
        class Quando_excluir {

            @BeforeEach
            void setUp() {
                when(amenidadeService.excluir(ID_INEXISTENTE))
                        .thenThrow(new RegistroNaoEncontradoException("Não encontrado"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(delete("/amenidades/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Dado_uma_amenidade_valida {

        private AmenidadeDto dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.amenidadeDtoComNome("Academia");
        }

        @Nested
        class Quando_criar_com_role_operacao {

            @BeforeEach
            void setUp() {
                Amenidade nova = mockFactory.amenidadeComIdENome(2L, "Academia");
                when(mapper.toEntity(any(AmenidadeDto.class))).thenReturn(nova);
                when(amenidadeService.salvar(any(Amenidade.class))).thenReturn(nova);
                when(mapper.toDTO(any(Amenidade.class))).thenReturn(mockFactory.amenidadeDtoComNome("Academia"));
            }

            @Test
            void Entao_deve_criar_a_amenidade() throws Exception {
                mockMvc.perform(post("/amenidades")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_amenidade-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.nome").value("Academia"));
            }
        }
    }

    @Nested
    class Dado_uma_amenidade_sem_nome {

        private AmenidadeDto dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.amenidadeDtoSemNome();
        }

        @Nested
        class Quando_criar {

            @Test
            void Entao_deve_informar_que_o_nome_e_obrigatorio() throws Exception {
                mockMvc.perform(post("/amenidades")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    class Dado_uma_amenidade_existente {

        private AmenidadeDto dto;

        @BeforeEach
        void setUp() {
            dto = mockFactory.amenidadeDto();
        }

        @Nested
        class Quando_atualizar_com_role_operacao {

            @BeforeEach
            void setUp() {
                when(mapper.toEntity(any(AmenidadeDto.class))).thenReturn(mockFactory.amenidadeExistente());
                when(amenidadeService.salvar(any(Amenidade.class))).thenReturn(mockFactory.amenidadeExistente());
                when(mapper.toDTO(any(Amenidade.class))).thenReturn(dto);
            }

            @Test
            void Entao_deve_atualizar_a_amenidade() throws Exception {
                mockMvc.perform(put("/amenidades")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_amenidade-operacao")))
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
                when(amenidadeService.buscarPorSpecification(eq("nome==Piscina"), any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.amenidadeExistente())));
                when(mapper.toDTO(any(Amenidade.class))).thenReturn(mockFactory.amenidadeDto());
            }

            @Test
            void Entao_deve_retornar_amenidades_filtradas() throws Exception {
                mockMvc.perform(get("/amenidades")
                                .param("search", "nome==Piscina")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].nome").value("Piscina"));
            }
        }
    }
}
