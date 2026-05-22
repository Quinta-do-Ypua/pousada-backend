package com.senai.pousadabackend.domain.reserva;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.reserva.dto.ReservaDTO;
import com.senai.pousadabackend.domain.reserva.dto.ReservaResumidaDto;
import com.senai.pousadabackend.domain.reserva.service.ReservaService;
import com.senai.pousadabackend.exceptions.CancelamentoDeReservaConcluidaException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    private Reserva reserva;
    private ReservaDTO reservaDTO;
    private ReservaResumidaDto resumidaDto;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        reserva.setCheckIn(LocalDateTime.now().plusDays(5));
        reserva.setCheckOut(LocalDateTime.now().plusDays(8));
        reserva.setValorDaReserva(BigDecimal.valueOf(300));
        reserva.setDataCriacao(LocalDateTime.now());

        reservaDTO = ReservaDTO.builder()
                .id(1L)
                .statusDaReserva(StatusDaReserva.ABERTA)
                .checkIn(LocalDateTime.now().plusDays(5))
                .checkOut(LocalDateTime.now().plusDays(8))
                .valorDaReserva(BigDecimal.valueOf(300))
                .build();

        resumidaDto = ReservaResumidaDto.builder()
                .quartoId(1L)
                .clienteId(1L)
                .checkIn(LocalDateTime.now().plusDays(5))
                .checkOut(LocalDateTime.now().plusDays(8))
                .build();
    }

    @Test
    void listar_semAutenticacao_retorna401() throws Exception {
        mockMvc.perform(get("/reservas"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listar_comRoleVisualizacao_retorna200() throws Exception {
        when(reservaService.listarPaginado(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(reserva)));
        when(reservaMapper.toDTO(any(Reserva.class))).thenReturn(reservaDTO);

        mockMvc.perform(get("/reservas")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_reserva-visualizacao"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    void buscarPorId_encontrado_retorna200() throws Exception {
        when(reservaService.buscarPorId(1L)).thenReturn(reserva);
        when(reservaMapper.toDTO(reserva)).thenReturn(reservaDTO);

        mockMvc.perform(get("/reservas/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_naoEncontrado_retorna404() throws Exception {
        when(reservaService.buscarPorId(99L))
                .thenThrow(new RegistroNaoEncontradoException("Reserva não encontrada"));

        mockMvc.perform(get("/reservas/99")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void cadastrar_comRoleOperacao_retorna200() throws Exception {
        when(reservaResumidaMapper.toReserva(any())).thenReturn(reserva);
        when(reservaService.salvar(any(Reserva.class))).thenReturn(reserva);
        when(reservaMapper.toDTO(reserva)).thenReturn(reservaDTO);

        mockMvc.perform(post("/reservas")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_reserva-operacao")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resumidaDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void cancelarReserva_sucesso_retorna200() throws Exception {
        ReservaDTO canceladaDTO = ReservaDTO.builder()
                .id(1L)
                .statusDaReserva(StatusDaReserva.CANCELADA)
                .build();
        reserva.setStatusDaReserva(StatusDaReserva.CANCELADA);

        when(reservaService.cancelarPorId(1L)).thenReturn(reserva);
        when(reservaMapper.toDTO(reserva)).thenReturn(canceladaDTO);

        mockMvc.perform(patch("/reservas/1/cancelar")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_reserva-operacao"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusDaReserva").value("CANCELADA"));
    }

    @Test
    void cancelarReserva_reservaConcluida_retorna400() throws Exception {
        when(reservaService.cancelarPorId(1L))
                .thenThrow(new CancelamentoDeReservaConcluidaException());

        mockMvc.perform(patch("/reservas/1/cancelar")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").exists());
    }

    @Test
    void cancelarReserva_naoEncontrada_retorna404() throws Exception {
        when(reservaService.cancelarPorId(99L))
                .thenThrow(new RegistroNaoEncontradoException("Reserva não encontrada"));

        mockMvc.perform(patch("/reservas/99/cancelar")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void alterar_comRoleOperacao_retorna200() throws Exception {
        when(reservaResumidaMapper.toReserva(any())).thenReturn(reserva);
        when(reservaService.salvar(any(Reserva.class))).thenReturn(reserva);
        when(reservaMapper.toDTO(reserva)).thenReturn(reservaDTO);

        mockMvc.perform(put("/reservas")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_reserva-operacao")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resumidaDto)))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorSearch_retornaResultadoFiltrado() throws Exception {
        when(reservaService.buscarPorSpecification(anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(reserva)));
        when(reservaMapper.toDTO(any(Reserva.class))).thenReturn(reservaDTO);

        mockMvc.perform(get("/reservas")
                        .param("search", "statusDaReserva==ABERTA")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_admin"))))
                .andExpect(status().isOk());
    }
}
