package com.senai.pousadabackend.domain.reserva.service;

import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.parametro.ParametroReservaService;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import com.senai.pousadabackend.exceptions.*;
import com.senai.pousadabackend.infraestructure.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ParametroReservaService parametroReservaService;

    @Mock
    private EmailService emailService;

    private ReservaService service;

    @BeforeEach
    void setUp() {
        service = new ReservaService(reservaRepository, parametroReservaService, emailService);
        configurarParametrosPadrao();
    }

    private void configurarParametrosPadrao() {
        when(parametroReservaService.getTempoMinimoParaReservaDias()).thenReturn(1);
        when(parametroReservaService.getDuracaoMinimaDias()).thenReturn(1);
        when(parametroReservaService.getDuracaoMaximaDias()).thenReturn(30);
        when(parametroReservaService.getTempoEntreReservasDias()).thenReturn(0);
        when(parametroReservaService.isBloquearReservaComPendencia()).thenReturn(true);
        when(parametroReservaService.getMaxReservasAtivasPorUsuario()).thenReturn(5);
        when(parametroReservaService.getPrazoMaximoCancelamentoDias()).thenReturn(7);
        when(parametroReservaService.isMultaCancelamentoAtiva()).thenReturn(false);
    }

    private Cliente clientePadrao() {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setNome("João");
        c.setEmail("joao@test.com");
        return c;
    }

    private Quarto quartoPadrao() {
        Quarto q = new Quarto();
        q.setId(1L);
        q.setNome("Suite 01");
        return q;
    }

    private Reserva reservaNovaValida() {
        LocalDateTime checkIn = LocalDateTime.now().plusDays(5);
        LocalDateTime checkOut = checkIn.plusDays(3);
        return Reserva.builder()
                .quarto(quartoPadrao())
                .cliente(clientePadrao())
                .checkIn(checkIn)
                .checkOut(checkOut)
                .valorDaReserva(BigDecimal.valueOf(300))
                .build();
    }

    private Reserva reservaExistente() {
        Reserva r = reservaNovaValida();
        r.setId(1L);
        r.setDataCriacao(LocalDateTime.now().minusDays(1));
        r.setStatusDaReserva(StatusDaReserva.ABERTA);
        return r;
    }

    // ---- salvar (nova reserva) ----

    @Test
    void salvar_novaReserva_statusNulo_defineStatusAberta() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(null);

        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any())).thenReturn(List.of());
        when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
        when(reservaRepository.findUltimoCheckOutPorCliente(any())).thenReturn(null);
        when(reservaRepository.save(any())).thenAnswer(inv -> {
            Reserva r = inv.getArgument(0);
            r.setId(1L);
            r.setDataCriacao(LocalDateTime.now());
            return r;
        });

        Reserva salva = service.salvar(reserva);

        assertThat(salva.getStatusDaReserva()).isEqualTo(StatusDaReserva.ABERTA);
        verify(emailService).enviar(contains("Nova reserva"), anyString(), any(Cliente.class));
    }

    @Test
    void salvar_novaReserva_sucesso_enviEmail() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);

        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any())).thenReturn(List.of());
        when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
        when(reservaRepository.findUltimoCheckOutPorCliente(any())).thenReturn(null);
        when(reservaRepository.save(any())).thenAnswer(inv -> {
            Reserva r = inv.getArgument(0);
            r.setId(2L);
            r.setDataCriacao(LocalDateTime.now());
            return r;
        });

        service.salvar(reserva);

        verify(emailService, times(1)).enviar(anyString(), anyString(), any(Cliente.class));
    }

    @Test
    void salvar_reservaExistente_enviEmailAlteracao() {
        Reserva existente = reservaExistente();

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(reservaRepository.saveAndFlush(any())).thenReturn(existente);

        service.salvar(existente);

        verify(emailService).enviar(contains("Alteração"), anyString(), any(Cliente.class));
    }

    // ---- validação de datas ----

    @Test
    void salvar_checkInNulo_lancaNullPointerException() {
        Reserva reserva = reservaNovaValida();
        reserva.setCheckIn(null);

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void salvar_checkOutNulo_lancaNullPointerException() {
        Reserva reserva = reservaNovaValida();
        reserva.setCheckOut(null);

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void salvar_checkInDepoisCheckOut_lancaDataDaReservaInvalida() {
        Reserva reserva = reservaNovaValida();
        reserva.setCheckIn(LocalDateTime.now().plusDays(5));
        reserva.setCheckOut(LocalDateTime.now().plusDays(3));

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(DataDaReservaInvalida.class);
    }

    @Test
    void salvar_checkInIgualCheckOut_lancaDataDaReservaInvalida() {
        Reserva reserva = reservaNovaValida();
        LocalDateTime agora = LocalDateTime.now().plusDays(5);
        reserva.setCheckIn(agora);
        reserva.setCheckOut(agora);

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(DataDaReservaInvalida.class);
    }

    // ---- validação de status inicial ----

    @Test
    void salvar_statusCancelado_lancaCancelamentoDeReservaConcluidaException() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.CANCELADA);

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(CancelamentoDeReservaConcluidaException.class);
    }

    @Test
    void salvar_statusFechado_lancaCancelamentoDeReservaConcluidaException() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.FECHADA);

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(CancelamentoDeReservaConcluidaException.class);
    }

    // ---- validação de disponibilidade do quarto ----

    @Test
    void salvar_quartoOcupado_lancaExisteReservaParaEssaDataException() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);

        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any()))
                .thenReturn(List.of(new Reserva()));

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(ExisteReservaParaEssaDataException.class);
    }

    // ---- validação de pendências do cliente ----

    @Test
    void salvar_limiteReservasExcedido_lancaLimiteReservasExcedidoException() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);

        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any())).thenReturn(List.of());
        when(parametroReservaService.getMaxReservasAtivasPorUsuario()).thenReturn(2);
        when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(2L);

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(LimiteReservasExcedidoException.class);
    }

    @Test
    void salvar_bloqueioDesativado_naoValidaPendencias() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);

        when(parametroReservaService.isBloquearReservaComPendencia()).thenReturn(false);
        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any())).thenReturn(List.of());
        when(reservaRepository.findUltimoCheckOutPorCliente(any())).thenReturn(null);
        when(reservaRepository.save(any())).thenAnswer(inv -> {
            Reserva r = inv.getArgument(0);
            r.setId(1L);
            r.setDataCriacao(LocalDateTime.now());
            return r;
        });

        assertThatCode(() -> service.salvar(reserva)).doesNotThrowAnyException();
        verify(reservaRepository, never()).countReservasAtivasPorCliente(any());
    }

    // ---- validação de prazo mínimo ----

    @Test
    void salvar_prazoMinimoNaoRespeitado_lancaPrazoMinimoNaoRespeitadoException() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        reserva.setCheckIn(LocalDateTime.now()); // now, but min is 1 day ahead
        reserva.setCheckOut(LocalDateTime.now().plusDays(2));

        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any())).thenReturn(List.of());
        when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
        when(parametroReservaService.getTempoMinimoParaReservaDias()).thenReturn(2);

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(PrazoMinimoNaoRespeitadoException.class);
    }

    // ---- validação de duração ----

    @Test
    void salvar_duracaoMenorQueMinimo_lancaDuracaoReservaInvalidaException() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        reserva.setCheckIn(LocalDateTime.now().plusDays(5));
        reserva.setCheckOut(LocalDateTime.now().plusDays(6)); // 1 day

        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any())).thenReturn(List.of());
        when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
        when(parametroReservaService.getDuracaoMinimaDias()).thenReturn(3);
        when(parametroReservaService.getDuracaoMaximaDias()).thenReturn(30);

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(DuracaoReservaInvalidaException.class);
    }

    @Test
    void salvar_duracaoMaiorQueMaximo_lancaDuracaoReservaInvalidaException() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        reserva.setCheckIn(LocalDateTime.now().plusDays(5));
        reserva.setCheckOut(LocalDateTime.now().plusDays(45)); // 40 days

        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any())).thenReturn(List.of());
        when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
        when(parametroReservaService.getDuracaoMinimaDias()).thenReturn(1);
        when(parametroReservaService.getDuracaoMaximaDias()).thenReturn(30);

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(DuracaoReservaInvalidaException.class);
    }

    // ---- validação de tempo entre reservas ----

    @Test
    void salvar_tempoEntreReservasNaoRespeitado_lancaTempoEntreReservasNaoRespeitadoException() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        reserva.setCheckIn(LocalDateTime.now().plusDays(5));
        reserva.setCheckOut(LocalDateTime.now().plusDays(8));

        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any())).thenReturn(List.of());
        when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
        when(parametroReservaService.getTempoEntreReservasDias()).thenReturn(10);
        // Last checkout was 2 days ago, need 10 days gap
        when(reservaRepository.findUltimoCheckOutPorCliente(any()))
                .thenReturn(LocalDateTime.now().minusDays(2));

        assertThatThrownBy(() -> service.salvar(reserva))
                .isInstanceOf(TempoEntreReservasNaoRespeitadoException.class);
    }

    @Test
    void salvar_semHistoricoDeReservas_passaValidacaoTempoEntreReservas() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);

        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any())).thenReturn(List.of());
        when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
        when(parametroReservaService.getTempoEntreReservasDias()).thenReturn(10);
        when(reservaRepository.findUltimoCheckOutPorCliente(any())).thenReturn(null);
        when(reservaRepository.save(any())).thenAnswer(inv -> {
            Reserva r = inv.getArgument(0);
            r.setId(1L);
            r.setDataCriacao(LocalDateTime.now());
            return r;
        });

        assertThatCode(() -> service.salvar(reserva)).doesNotThrowAnyException();
    }

    @Test
    void salvar_tempoEntreReservasZero_ignoraValidacao() {
        Reserva reserva = reservaNovaValida();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);

        when(parametroReservaService.getTempoEntreReservasDias()).thenReturn(0);
        when(reservaRepository.findQuartosEntreCheckInECheckOut(any(), any(), any())).thenReturn(List.of());
        when(reservaRepository.countReservasAtivasPorCliente(any())).thenReturn(0L);
        when(reservaRepository.save(any())).thenAnswer(inv -> {
            Reserva r = inv.getArgument(0);
            r.setId(1L);
            r.setDataCriacao(LocalDateTime.now());
            return r;
        });

        assertThatCode(() -> service.salvar(reserva)).doesNotThrowAnyException();
        verify(reservaRepository, never()).findUltimoCheckOutPorCliente(any());
    }

    // ---- cancelarPorId ----

    @Test
    void cancelarPorId_sucesso_atualizaStatusEEnviaEmail() {
        Reserva reserva = reservaExistente();
        reserva.setCheckIn(LocalDateTime.now().plusDays(10));

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.saveAndFlush(any())).thenReturn(reserva);

        Reserva cancelada = service.cancelarPorId(1L);

        assertThat(cancelada.getStatusDaReserva()).isEqualTo(StatusDaReserva.CANCELADA);
        verify(emailService).enviar(contains("Cancelamento"), anyString(), any(Cliente.class));
    }

    @Test
    void cancelarPorId_reservaNaoEncontrada_lancaRegistroNaoEncontradoException() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.cancelarPorId(99L))
                .isInstanceOf(RegistroNaoEncontradoException.class);
    }

    @Test
    void cancelarPorId_reservaConcluida_lancaCancelamentoDeReservaConcluidaException() {
        Reserva reserva = reservaExistente();
        reserva.setStatusDaReserva(StatusDaReserva.CONCLUIDA);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        assertThatThrownBy(() -> service.cancelarPorId(1L))
                .isInstanceOf(CancelamentoDeReservaConcluidaException.class);
    }

    @Test
    void cancelarPorId_reservaFechada_lancaCancelamentoDeReservaConcluidaException() {
        Reserva reserva = reservaExistente();
        reserva.setStatusDaReserva(StatusDaReserva.FECHADA);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        assertThatThrownBy(() -> service.cancelarPorId(1L))
                .isInstanceOf(CancelamentoDeReservaConcluidaException.class);
    }

    @Test
    void cancelarPorId_prazoExcedido_lancaPrazoCancelamentoExcedidoException() {
        Reserva reserva = reservaExistente();
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        // checkIn in 2 days, but prazo máximo is 7 → limit is checkIn - 7 = 5 days ago → now is after limit
        reserva.setCheckIn(LocalDateTime.now().plusDays(2));

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(parametroReservaService.getPrazoMaximoCancelamentoDias()).thenReturn(7);

        assertThatThrownBy(() -> service.cancelarPorId(1L))
                .isInstanceOf(PrazoCancelamentoExcedidoException.class);
    }

    @Test
    void cancelarPorId_multaInativa_retornaZero() {
        Reserva reserva = reservaExistente();
        reserva.setCheckIn(LocalDateTime.now().plusDays(30));
        reserva.setStatusDaReserva(StatusDaReserva.ABERTA);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(parametroReservaService.isMultaCancelamentoAtiva()).thenReturn(false);
        when(reservaRepository.saveAndFlush(any())).thenReturn(reserva);

        service.cancelarPorId(1L);

        verify(parametroReservaService, times(1)).isMultaCancelamentoAtiva();
    }

    // ---- buscarPorQuarto ----

    @Test
    void buscarPorQuarto_retornaListaDeReservas() {
        Quarto quarto = quartoPadrao();
        List<Reserva> lista = List.of(reservaExistente());
        when(reservaRepository.findByQuarto(quarto)).thenReturn(lista);

        List<Reserva> resultado = service.buscarPorQuarto(quarto);

        assertThat(resultado).hasSize(1);
    }

    // ---- listarPaginado (override) ----

    @Test
    void listarPaginado_usaBuscarReservasAtivas() {
        var pageable = PageRequest.of(0, 15);
        Page<Reserva> page = new PageImpl<>(List.of(reservaExistente()));
        when(reservaRepository.buscarReservasAtivas(pageable)).thenReturn(page);

        Page<Reserva> resultado = service.listarPaginado(pageable);

        assertThat(resultado.getContent()).hasSize(1);
        verify(reservaRepository).buscarReservasAtivas(pageable);
    }
}
