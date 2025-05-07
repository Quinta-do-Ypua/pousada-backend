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
import java.util.Objects;

@Service
public class ReservaServiceImpl extends BaseService<Reserva, Long, ReservaRepository> implements ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaServiceImpl(ReservaRepository repo, ReservaRepository reservaRepository) {
        super(repo);
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Reserva salvar(Reserva reserva) {

        if (reserva.isNovo()) {
            checarSePodeReservar(reserva);
            prepararReservaNova(reserva);
        }

        return super.salvar(reserva);
    }

    @Override
    public Reserva cancelarPorId(Long id) {
        Reserva reserva = buscarPorId(id);
        checarSeReservaPodeCancelar(reserva);
        reserva.setStatus(StatusDaReserva.CANCELADA);
        this.salvar(reserva);
        return reserva;
    }

    private void checarSeReservaPodeCancelar(Reserva reserva) {
        if (reserva.getStatus().equals(StatusDaReserva.CONCLUIDA)
                || reserva.getStatus().equals(StatusDaReserva.FECHADA))
            throw new CancelamentoDeReservaConcluidaException();
    }

    private void prepararReservaNova(Reserva reserva) {
        if (Objects.isNull(reserva.getStatus())) {
            reserva.setStatus(StatusDaReserva.ABERTA);
        } else {
            if (reserva.getStatus().equals(StatusDaReserva.CANCELADA)
                    || reserva.getStatus().equals(StatusDaReserva.FECHADA))
                throw new CancelamentoDeReservaConcluidaException("Não é possível criar uma reserva cancelada ou fechada.");
        }

        reserva.setValorDaDiariaDoQuarto(reserva.getQuarto().getValorDiaria());

        BigDecimal valorComplementos = reserva.getComplementos().stream()
                .map(Complemento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        reserva.setValorComplementos(valorComplementos);

        long dias = Duration.between(reserva.getCheckIn(), reserva.getCheckOut()).toDays();
        reserva.setValorTotalDoQuarto(reserva.getValorDaDiariaDoQuarto().multiply(BigDecimal.valueOf(dias)));

    }



    private void checarSePodeReservar(Reserva reserva) {

        verificarPendenciasDoCliente(reserva.getCliente());

        if (reserva.getCheckIn() == null || reserva.getCheckOut() == null) {
            throw new NullPointerException("A data de CheckIn e CheckOut são obrigatórias");
        }

        if (reserva.getCheckIn().isAfter(reserva.getCheckOut())
                || reserva.getCheckOut().isBefore(reserva.getCheckIn())) {
            throw new DataDaReservaInvalida();
        }

        if(!reservaRepository.findQuartosEntreCheckInECheckOut(reserva.getCheckIn(), reserva.getCheckOut(), reserva.getQuarto()).isEmpty())
            throw new ExisteReservaParaEssaDataException();
    }

    private void verificarPendenciasDoCliente(Cliente cliente) {
        var reservas = reservaRepository.findReservaByCliente(cliente);
        boolean hasReservaAberta = reservas.stream()
                .anyMatch(r -> r.getStatus() == StatusDaReserva.ABERTA);
        if (hasReservaAberta) {
            throw new ExisteReservaAbertaParaEsseCliente();
        }
    }
}
