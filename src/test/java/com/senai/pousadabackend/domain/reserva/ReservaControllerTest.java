package com.senai.pousadabackend.domain.reserva;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.MockFactory;
import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.reserva.dto.ReservaDTO;
import com.senai.pousadabackend.domain.reserva.dto.ReservaResumidaDto;
import com.senai.pousadabackend.exceptions.CancelamentoDeReservaConcluidaException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservaService reservaService;

    @MockitoBean
    private ReservaMapper reservaMapper;

    @MockitoBean
    private ReservaResumidaMapper reservaResumidaMapper;

    @MockitoBean
    private JwtDecoder jwtDecoder;

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
        class Quando_listar_reservas {

            @Test
            void Entao_deve_exigir_autenticacao() throws Exception {
                mockMvc.perform(get("/reservas"))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_visualizacao {

        @Nested
        class Quando_listar_reservas {

            @BeforeEach
            void setUp() {
                when(reservaService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.reservaExistente())));
                when(reservaMapper.toDTO(any(Reserva.class))).thenReturn(mockFactory.reservaDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_reservas() throws Exception {
                mockMvc.perform(get("/reservas")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_reserva-visualizacao"))))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].id").value(1));
            }
        }
    }

    @Nested
    class Dado_uma_requisicao_com_role_admin {

        @Nested
        class Quando_listar_reservas {

            @BeforeEach
            void setUp() {
                when(reservaService.listarPaginado(any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.reservaExistente())));
                when(reservaMapper.toDTO(any(Reserva.class))).thenReturn(mockFactory.reservaDTO());
            }

            @Test
            void Entao_deve_retornar_a_lista_de_reservas() throws Exception {
                mockMvc.perform(get("/reservas")
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
                when(reservaService.buscarPorId(idExistente)).thenReturn(mockFactory.reservaExistente());
                when(reservaMapper.toDTO(any(Reserva.class))).thenReturn(mockFactory.reservaDTO());
            }

            @Test
            void Entao_deve_retornar_a_reserva() throws Exception {
                mockMvc.perform(get("/reservas/" + idExistente)
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
                when(reservaService.buscarPorId(ID_INEXISTENTE))
                        .thenThrow(new RegistroNaoEncontradoException("Reserva não encontrada"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(get("/reservas/" + ID_INEXISTENTE)
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        class Quando_cancelar {

            @BeforeEach
            void setUp() {
                when(reservaService.cancelarPorId(ID_INEXISTENTE))
                        .thenThrow(new RegistroNaoEncontradoException("Reserva não encontrada"));
            }

            @Test
            void Entao_deve_informar_que_o_registro_nao_foi_encontrado() throws Exception {
                mockMvc.perform(patch("/reservas/" + ID_INEXISTENTE + "/cancelar")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Dado_uma_reserva_valida {

        private ReservaResumidaDto resumidaDto;

        @BeforeEach
        void setUp() {
            resumidaDto = mockFactory.reservaResumidaDto();
        }

        @Nested
        class Quando_cadastrar_com_role_operacao {

            @BeforeEach
            void setUp() {
                when(reservaResumidaMapper.toReserva(any())).thenReturn(mockFactory.novaReserva());
                when(reservaService.salvar(any(Reserva.class))).thenReturn(mockFactory.reservaExistente());
                when(reservaMapper.toDTO(any(Reserva.class))).thenReturn(mockFactory.reservaDTO());
            }

            @Test
            void Entao_deve_criar_a_reserva() throws Exception {
                mockMvc.perform(post("/reservas")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_reserva-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(resumidaDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1));
            }
        }
    }

    @Nested
    class Dado_uma_reserva_existente {

        private ReservaResumidaDto resumidaDto;

        @BeforeEach
        void setUp() {
            resumidaDto = mockFactory.reservaResumidaDto();
        }

        @Nested
        class Quando_alterar_com_role_operacao {

            @BeforeEach
            void setUp() {
                when(reservaResumidaMapper.toReserva(any())).thenReturn(mockFactory.reservaExistente());
                when(reservaService.salvar(any(Reserva.class))).thenReturn(mockFactory.reservaExistente());
                when(reservaMapper.toDTO(any(Reserva.class))).thenReturn(mockFactory.reservaDTO());
            }

            @Test
            void Entao_deve_atualizar_a_reserva() throws Exception {
                mockMvc.perform(put("/reservas")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_reserva-operacao")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(resumidaDto)))
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    class Dado_uma_reserva_aberta {

        private Long idExistente = 1L;

        @Nested
        class Quando_cancelar {

            @BeforeEach
            void setUp() {
                Reserva cancelada = mockFactory.reservaExistente();
                cancelada.setStatusDaReserva(StatusDaReserva.CANCELADA);
                
                when(reservaService.cancelarPorId(idExistente)).thenReturn(cancelada);
                when(reservaMapper.toDTO(any(Reserva.class))).thenReturn(mockFactory.reservaDTO());
            }

            @Test
            void Entao_deve_cancelar_a_reserva() throws Exception {
                mockMvc.perform(patch("/reservas/" + idExistente + "/cancelar")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_reserva-operacao"))))
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    class Dado_uma_reserva_concluida {

        private Long idExistente = 1L;

        @Nested
        class Quando_cancelar {

            @BeforeEach
            void setUp() {
                when(reservaService.cancelarPorId(idExistente))
                        .thenThrow(new CancelamentoDeReservaConcluidaException());
            }

            @Test
            void Entao_deve_informar_que_nao_e_possivel_cancelar() throws Exception {
                mockMvc.perform(patch("/reservas/" + idExistente + "/cancelar")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.mensagem").exists());
            }
        }
    }

    @Nested
    class Dado_uma_busca_com_filtro {

        @Nested
        class Quando_buscar_por_specification {

            @BeforeEach
            void setUp() {
                when(reservaService.buscarPorSpecification(anyString(), any(Pageable.class)))
                        .thenReturn(new PageImpl<>(List.of(mockFactory.reservaExistente())));
                when(reservaMapper.toDTO(any(Reserva.class))).thenReturn(mockFactory.reservaDTO());
            }

            @Test
            void Entao_deve_retornar_reservas_filtradas() throws Exception {
                mockMvc.perform(get("/reservas")
                                .param("search", "statusDaReserva==ABERTA")
                                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                        .andExpect(status().isOk());
            }
        }
    }
}
