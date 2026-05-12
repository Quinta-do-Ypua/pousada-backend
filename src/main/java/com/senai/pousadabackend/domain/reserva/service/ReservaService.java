package com.senai.pousadabackend.domain.reserva.service;

import com.senai.pousadabackend.core.base.BaseService;
import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.infraestructure.email.EmailService;
import com.senai.pousadabackend.domain.parametro.ParametroReservaService;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import com.senai.pousadabackend.exceptions.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservaService extends BaseService<Reserva, Long, ReservaRepository> {

    private final ReservaRepository reservaRepository;
    private final ParametroReservaService parametroReservaService;
    private final EmailService emailService;

    public ReservaService(ReservaRepository repo,
                          ParametroReservaService parametroReservaService,
                          EmailService emailService) {
        super(repo);
        this.reservaRepository = repo;
        this.parametroReservaService = parametroReservaService;
        this.emailService = emailService;
    }

    @Override
    public Reserva salvar(Reserva reserva) {
        boolean isNova = reserva.isNovo();
        if (isNova) {
            inicializarReserva(reserva);
        }
        Reserva salva = super.salvar(reserva);
        if (isNova) {
            emailService.enviar("Nova reserva!",
                    "Sua reserva do quarto " + salva.getQuarto() + " de número " + salva.getId() + " foi efetuada com sucesso!",
                    salva.getCliente());
        } else {
            emailService.enviar("Alteração na sua reserva do quarto: " + salva.getQuarto(),
                    "Sua reserva foi alterada: " + salva,
                    salva.getCliente());
        }
        return salva;
    }

    @Transactional
    public Reserva cancelarPorId(Long id) {
        Reserva reserva = buscarPorId(id);
        validarCancelamento(reserva);
        calcularMultaCancelamento(reserva);
        reserva.setStatusDaReserva(StatusDaReserva.CANCELADA);
        Reserva cancelada = super.salvar(reserva);
        emailService.enviar("Cancelamento de reserva",
                "Sua reserva do quarto " + cancelada.getQuarto() + " de número " + cancelada.getId() + " foi cancelada.",
                cancelada.getCliente());
        return cancelada;
    }

    public List<Reserva> buscarPorQuarto(Quarto quarto) {
        return reservaRepository.findByQuarto(quarto);
    }

    @Override
    public Page<Reserva> listarPaginado(Pageable pageable) {
        return reservaRepository.buscarReservasAtivas(pageable);
    }

    private void inicializarReserva(Reserva reserva) {
        definirStatusPadrao(reserva);
        validarNovaReserva(reserva);
    }

    private void validarNovaReserva(Reserva reserva) {
        validarDatas(reserva);
        validarStatusInicial(reserva);
        validarDisponibilidadeDoQuarto(reserva);
        validarPendenciasDoCliente(reserva.getCliente());
        validarPrazoMinimoReserva(reserva);
        validarDuracaoMinimaMaxima(reserva);
        validarTempoEntreReservas(reserva);
    }

    private void validarCancelamento(Reserva reserva) {
        if (reserva.getStatusDaReserva() == StatusDaReserva.CONCLUIDA
                || reserva.getStatusDaReserva() == StatusDaReserva.FECHADA) {
            throw new CancelamentoDeReservaConcluidaException();
        }

        Integer prazoMaximoDias = parametroReservaService.getPrazoMaximoCancelamentoDias();
        LocalDateTime limiteCancelamento = reserva.getCheckIn().minusDays(prazoMaximoDias);

        if (LocalDateTime.now().isAfter(limiteCancelamento)) {
            throw new PrazoCancelamentoExcedidoException(prazoMaximoDias);
        }
    }

    private BigDecimal calcularMultaCancelamento(Reserva reserva) {
        if (!parametroReservaService.isMultaCancelamentoAtiva()) {
            return BigDecimal.ZERO;
        }

        Integer prazoMaximoDias = parametroReservaService.getPrazoMaximoCancelamentoDias();
        LocalDateTime limiteCancelamento = reserva.getCheckIn().minusDays(prazoMaximoDias);

        if (LocalDateTime.now().isAfter(limiteCancelamento)) {
            BigDecimal percentual = parametroReservaService.getPercentualMultaCancelamento();
            return reserva.getValorDaReserva()
                    .multiply(percentual)
                    .divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
        }

        return BigDecimal.ZERO;
    }

    private void validarDatas(Reserva reserva) {
        if (reserva.getCheckIn() == null || reserva.getCheckOut() == null) {
            throw new NullPointerException("A data de CheckIn e CheckOut são obrigatórias");
        }
        if (!reserva.getCheckIn().isBefore(reserva.getCheckOut())) {
            throw new DataDaReservaInvalida();
        }
    }

    private void validarStatusInicial(Reserva reserva) {
        if (reserva.getStatusDaReserva() == StatusDaReserva.CANCELADA
                || reserva.getStatusDaReserva() == StatusDaReserva.FECHADA) {
            throw new CancelamentoDeReservaConcluidaException("Não é possível criar uma reserva cancelada ou fechada.");
        }
    }

    private void validarDisponibilidadeDoQuarto(Reserva reserva) {
        boolean quartoOcupado = !reservaRepository.findQuartosEntreCheckInECheckOut(
                reserva.getCheckIn(), reserva.getCheckOut(), reserva.getQuarto()).isEmpty();
        if (quartoOcupado) {
            throw new ExisteReservaParaEssaDataException();
        }
    }

    private void validarPendenciasDoCliente(Cliente cliente) {
        if (!parametroReservaService.isBloquearReservaComPendencia()) {
            return;
        }
        Integer maxReservas = parametroReservaService.getMaxReservasAtivasPorUsuario();
        Long reservasAtivas = reservaRepository.countReservasAtivasPorCliente(cliente);
        if (reservasAtivas >= maxReservas) {
            throw new LimiteReservasExcedidoException(maxReservas);
        }
    }

    private void validarPrazoMinimoReserva(Reserva reserva) {
        Integer minDias = parametroReservaService.getTempoMinimoParaReservaDias();
        LocalDateTime dataMinima = LocalDateTime.now().plusDays(minDias);
        if (reserva.getCheckIn().isBefore(dataMinima)) {
            throw new PrazoMinimoNaoRespeitadoException(minDias);
        }
    }

    private void validarDuracaoMinimaMaxima(Reserva reserva) {
        Integer minDias = parametroReservaService.getDuracaoMinimaDias();
        Integer maxDias = parametroReservaService.getDuracaoMaximaDias();
        long duracaoDias = ChronoUnit.DAYS.between(
                reserva.getCheckIn().toLocalDate(),
                reserva.getCheckOut().toLocalDate());
        if (duracaoDias < minDias || duracaoDias > maxDias) {
            throw new DuracaoReservaInvalidaException(minDias, maxDias);
        }
    }

    private void validarTempoEntreReservas(Reserva reserva) {
        Integer diasEntreReservas = parametroReservaService.getTempoEntreReservasDias();
        if (diasEntreReservas <= 0) {
            return;
        }
        Cliente cliente = reserva.getCliente();
        LocalDateTime ultimoCheckOut = reservaRepository.findUltimoCheckOutPorCliente(cliente);
        if (ultimoCheckOut == null) {
            return;
        }
        LocalDateTime dataMinimaNovaReserva = ultimoCheckOut.plusDays(diasEntreReservas);
        if (reserva.getCheckIn().isBefore(dataMinimaNovaReserva)) {
            throw new TempoEntreReservasNaoRespeitadoException(diasEntreReservas);
        }
    }

    private void definirStatusPadrao(Reserva reserva) {
        if (reserva.getStatusDaReserva() == null) {
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        }
    }

}
