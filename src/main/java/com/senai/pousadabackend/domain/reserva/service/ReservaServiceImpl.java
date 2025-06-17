package com.senai.pousadabackend.domain.reserva.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.quarto.service.QuartoService;
import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import com.senai.pousadabackend.domain.reserva.StatusDaReserva;
import com.senai.pousadabackend.domain.resumo.ResumoReservaService;
import com.senai.pousadabackend.exceptions.CancelamentoDeReservaConcluidaException;
import com.senai.pousadabackend.exceptions.DataDaReservaInvalida;
import com.senai.pousadabackend.exceptions.ExisteReservaAbertaParaEsseCliente;
import com.senai.pousadabackend.exceptions.ExisteReservaParaEssaDataException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReservaServiceImpl extends BaseService<Reserva, Long, ReservaRepository> implements ReservaService {

    private final ReservaRepository reservaRepository;

    private final ResumoReservaService resumoReservaService;
    private final QuartoService quartoService;

    public ReservaServiceImpl(ReservaRepository repo,
                              @Qualifier("resumoReservaServiceProxy") ResumoReservaService resumoReservaService, QuartoService quartoService) {
        super(repo);
        this.reservaRepository = repo;
        this.resumoReservaService = resumoReservaService;
        this.quartoService = quartoService;
    }

    @Override
    public Reserva salvar(Reserva reserva) {
        if (reserva.getId() == null) {
            inicializarReserva(reserva);
        }
        return super.salvar(reserva);
    }

    @Override
    public Reserva cancelarPorId(Long id) {
        Reserva reserva = buscarPorId(id);
        validarCancelamento(reserva);
        reserva.setStatusDaReserva(StatusDaReserva.CANCELADA);
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
        validarNovaReserva(reserva);
//        resumoReservaService.criarERetornarNotaFiscalAPartirDaReserva(reserva);
    }

    private void validarCancelamento(Reserva reserva) {
        if (reserva.getStatusDaReserva() == StatusDaReserva.CONCLUIDA
                || reserva.getStatusDaReserva() == StatusDaReserva.FECHADA) {
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
        boolean clienteTemReservaAberta = reservaRepository.findReservaByCliente(cliente).stream()
                .anyMatch(r -> r.getStatusDaReserva() == StatusDaReserva.ABERTA);

        if (clienteTemReservaAberta) {
            throw new ExisteReservaAbertaParaEsseCliente();
        }
    }

    private void definirStatusPadrao(Reserva reserva) {
        if (reserva.getStatusDaReserva() == null) {
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        }
    }

}
