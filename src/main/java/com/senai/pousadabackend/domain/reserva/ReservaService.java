package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.core.base.BaseService;
import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.cliente.Cliente;
import java.time.format.DateTimeFormatter;
import com.senai.pousadabackend.domain.complemento.ComplementoService;
import com.senai.pousadabackend.domain.cupom.Cupom;
import com.senai.pousadabackend.domain.cupom.CupomService;
import com.senai.pousadabackend.infraestructure.email.EmailService;
import com.senai.pousadabackend.domain.parametro.ParametroReservaService;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReservaService extends BaseService<Reserva, Long, ReservaRepository> {

    private final ReservaRepository reservaRepository;
    private final ParametroReservaService parametroReservaService;
    private final EmailService emailService;
    private final CupomService cupomService;
    private final ComplementoService complementoService;

    public ReservaService(ReservaRepository repo,
                          ParametroReservaService parametroReservaService,
                          EmailService emailService,
                          CupomService cupomService,
                          ComplementoService complementoService) {
        super(repo);
        this.reservaRepository = repo;
        this.parametroReservaService = parametroReservaService;
        this.emailService = emailService;
        this.cupomService = cupomService;
        this.complementoService = complementoService;
    }

    @Override
    @Transactional
    public Reserva salvar(Reserva reserva) {
        boolean isNova = reserva.isNovo();
        if (isNova) {
            inicializarReserva(reserva);
        } else {
            preservarCupomExistente(reserva);
        }
        Reserva salva = super.salvar(reserva);
        try {
            if (isNova) {
                emailService.enviar("Nova reserva!",
                        "Sua reserva do quarto " + salva.getQuarto().getNome() + " (nº " + salva.getId() + ") foi efetuada com sucesso!",
                        salva.getCliente());
            } else {
                emailService.enviar("Alteração na sua reserva",
                        "Sua reserva nº " + salva.getId() + " do quarto " + salva.getQuarto().getNome() + " foi alterada com sucesso.",
                        salva.getCliente());
            }
        } catch (Exception e) {
            log.warn("Falha ao enviar e-mail de notificação para reserva {}: {}", salva.getId(), e.getMessage());
        }
        return salva;
    }

    @Transactional
    public Reserva cancelarPorId(Long id) {
        Reserva reserva = buscarPorId(id);
        validarCancelamento(reserva);
        BigDecimal multa = calcularMultaCancelamento(reserva);
        if (multa.compareTo(BigDecimal.ZERO) > 0) {
            reserva.setValorDaReserva(reserva.getValorDaReserva().add(multa));
        }
        reserva.setStatusDaReserva(StatusDaReserva.CANCELADA);
        Reserva cancelada = super.salvar(reserva);
        try {
            String mensagemEmail = "Sua reserva nº " + cancelada.getId() +
                    " do quarto " + cancelada.getQuarto().getNome() + " foi cancelada.";
            if (multa.compareTo(BigDecimal.ZERO) > 0) {
                mensagemEmail += " Multa por cancelamento tardio: R$ " +
                        multa.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString() + ".";
            }
            emailService.enviar("Cancelamento de reserva", mensagemEmail, cancelada.getCliente());
        } catch (Exception e) {
            log.warn("Falha ao enviar e-mail de cancelamento para reserva {}: {}", cancelada.getId(), e.getMessage());
        }
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
        reserva.setValorDaReserva(calcularValorBase(reserva));
        validarNovaReserva(reserva);
    }

    private BigDecimal calcularValorBase(Reserva reserva) {
        long dias = ChronoUnit.DAYS.between(
                reserva.getCheckIn().toLocalDate(),
                reserva.getCheckOut().toLocalDate());
        BigDecimal valorDiarias = reserva.getQuarto().getValorDiaria()
                .multiply(BigDecimal.valueOf(dias));
        BigDecimal valorComplementos = Optional.ofNullable(reserva.getComplementos())
                .orElse(List.of())
                .stream()
                .map(c -> complementoService.buscarPorId(c.getId()).getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return valorDiarias.add(valorComplementos);
    }

    private void validarNovaReserva(Reserva reserva) {
        validarDatas(reserva);
        validarStatusInicial(reserva);
        validarDisponibilidadeDoQuarto(reserva);
        validarPendenciasDoCliente(reserva.getCliente());
        validarPrazoMinimoReserva(reserva);
        validarDuracaoMinimaMaxima(reserva);
        validarTempoEntreReservas(reserva);
        aplicarCupomSePresente(reserva);
    }

    private void preservarCupomExistente(Reserva reserva) {
        validarDisponibilidadeDoQuarto(reserva);
        validarDatas(reserva);
        Reserva existente = buscarPorId(reserva.getId());
        BigDecimal valorBase = calcularValorBase(reserva);
        Cupom cupom = existente.getCupom();
        reserva.setCupom(cupom);
        if (cupom != null) {
            BigDecimal porcentagem = BigDecimal.valueOf(cupom.getPorcentagemDeDesconto());
            BigDecimal desconto = valorBase
                    .multiply(porcentagem)
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            reserva.setDescontoCupom(desconto);
            reserva.setValorDaReserva(valorBase.subtract(desconto));
        } else {
            reserva.setDescontoCupom(BigDecimal.ZERO);
            reserva.setValorDaReserva(valorBase);
        }
    }

    private void aplicarCupomSePresente(Reserva reserva) {
        if (reserva.getCupom() == null) return;
        Cupom cupomValido = cupomService.buscarCupomValido(reserva.getCupom().getCodigo());
        reserva.setCupom(cupomValido);
        BigDecimal porcentagem = BigDecimal.valueOf(cupomValido.getPorcentagemDeDesconto());
        BigDecimal desconto = reserva.getValorDaReserva()
                .multiply(porcentagem)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        reserva.setDescontoCupom(desconto);
        reserva.setValorDaReserva(reserva.getValorDaReserva().subtract(desconto));
    }

    private void validarCancelamento(Reserva reserva) {
        if (reserva.getStatusDaReserva() == StatusDaReserva.CONCLUIDA) {
            throw new CancelamentoDeReservaConcluidaException();
        }

        Integer prazoMaximoDias = parametroReservaService.getPrazoMaximoCancelamentoDias();
        LocalDateTime limiteCancelamento = reserva.getCheckIn().minusDays(prazoMaximoDias);
        boolean prazoExcedido = LocalDateTime.now().isAfter(limiteCancelamento);

        // Só bloqueia se prazo excedido E multa inativa (sem multa = sem cancelamento tardio)
        if (prazoExcedido && !parametroReservaService.isMultaCancelamentoAtiva()) {
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
        if (reserva.getStatusDaReserva() == StatusDaReserva.CANCELADA) {
            throw new CancelamentoDeReservaConcluidaException("Não é possível criar uma reserva cancelada.");
        }
    }

    private void validarDisponibilidadeDoQuarto(Reserva reserva) {
        List<Reserva> conflitos = reservaRepository.findConflitosDeQuarto(
                reserva.getCheckIn(),
                reserva.getCheckOut(),
                reserva.getQuarto(),
                reserva.getId()
        );

        if (!conflitos.isEmpty()) {
            Reserva conflito = conflitos.get(0);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String de = conflito.getCheckIn().toLocalDate().format(fmt);
            String ate = conflito.getCheckOut().toLocalDate().format(fmt);
            throw new ExisteReservaParaEssaDataException(
                "O quarto '" + reserva.getQuarto().getNome() + "' já está reservado de " + de + " até " + ate +
                ". Escolha um período sem conflito."
            );
        }
    }

    private void validarPendenciasDoCliente(Cliente cliente) {
        Long reservasAtivas = reservaRepository.countReservasAtivasPorCliente(cliente);
        Integer maxReservas = parametroReservaService.getMaxReservasAtivasPorUsuario();

        if (parametroReservaService.isBloquearReservaComPendencia()) {
            if (reservasAtivas >= 1) {
                throw new BusinessException(
                    "Não é possível criar uma nova reserva para este cliente, pois ele já possui uma reserva ativa."
                );
            }
        } else {
            if (reservasAtivas >= maxReservas) {
                throw new LimiteReservasExcedidoException(maxReservas);
            }
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
