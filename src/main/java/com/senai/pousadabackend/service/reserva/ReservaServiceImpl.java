package com.senai.pousadabackend.service.reserva;

import com.senai.pousadabackend.entity.Cliente;
import com.senai.pousadabackend.entity.Complemento;
import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.entity.enums.StatusDaReserva;
import com.senai.pousadabackend.exceptions.CancelamentoDeReservaConcluidaException;
import com.senai.pousadabackend.exceptions.DataDaReservaInvalida;
import com.senai.pousadabackend.exceptions.ExisteReservaAbertaParaEsseCliente;
import com.senai.pousadabackend.exceptions.ExisteReservaParaEssaDataException;
import com.senai.pousadabackend.repository.ReservaRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
public class ReservaServiceImpl extends BaseService<Reserva, Long, ReservaRepository> implements ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaServiceImpl(ReservaRepository repo, ReservaRepository reservaRepository, ReservaRepository reservaRepository1) {
        super(repo);
        this.reservaRepository = reservaRepository1;
    }

    @Override
    public Reserva cancelarPorId(Long id) {
        Reserva reserva = buscarPorId(id);
        validarCancelamento(reserva);
        reserva.setStatus(StatusDaReserva.CANCELADA);
        this.salvar(reserva);
        return reserva;
    }

    private void validarNovaReserva(Reserva reserva) {
        validarDatas(reserva);
        validarStatusInicial(reserva);
        validarDisponibilidadeDoQuarto(reserva);
        validarPendenciasDoCliente(reserva.getCliente());
    }

    private void inicializarReserva(Reserva reserva) {
        definirStatusPadrao(reserva);
        calcularValoresDaReserva(reserva);
    }

    private void validarCancelamento(Reserva reserva) {
        if (reserva.getStatus() == StatusDaReserva.CONCLUIDA
                || reserva.getStatus() == StatusDaReserva.FECHADA) {
            throw new CancelamentoDeReservaConcluidaException();
        }
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
        if (reserva.getStatus() == StatusDaReserva.CANCELADA
                || reserva.getStatus() == StatusDaReserva.FECHADA) {
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
        boolean clienteTemReservaAberta = reservaRepository.findReservaByCliente(cliente).stream()
                .anyMatch(r -> r.getStatus() == StatusDaReserva.ABERTA);

        if (clienteTemReservaAberta) {
            throw new ExisteReservaAbertaParaEsseCliente();
        }
    }

    private void definirStatusPadrao(Reserva reserva) {
        if (reserva.getStatus() == null) {
            reserva.setStatus(StatusDaReserva.ABERTA);
        }
    }

    private void calcularValoresDaReserva(Reserva reserva) {
        reserva.setValorDaDiariaDoQuarto(reserva.getQuarto().getValorDiaria());

//        BigDecimal valorComplementos = reserva.getComplementos().stream()
//                .map(Complemento::getValor)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //reserva.setValorComplementos(valorComplementos);

        long dias = Duration.between(reserva.getCheckIn(), reserva.getCheckOut()).toDays();
        reserva.setValorTotalDoQuarto(
                reserva.getValorDaDiariaDoQuarto().multiply(BigDecimal.valueOf(dias))
        );
    }

}
