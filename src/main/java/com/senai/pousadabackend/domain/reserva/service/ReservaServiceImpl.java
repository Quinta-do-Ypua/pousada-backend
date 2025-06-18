package com.senai.pousadabackend.domain.reserva.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import com.senai.pousadabackend.domain.reserva.StatusDaReserva;
import com.senai.pousadabackend.exceptions.CancelamentoDeReservaConcluidaException;
import com.senai.pousadabackend.exceptions.DataDaReservaInvalida;
import com.senai.pousadabackend.exceptions.ExisteReservaAbertaParaEsseCliente;
import com.senai.pousadabackend.exceptions.ExisteReservaParaEssaDataException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl extends BaseService<Reserva, Long, ReservaRepository> implements ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaServiceImpl(ReservaRepository repo) {
        super(repo);
        this.reservaRepository = repo;
    }

    @Override
    public Reserva salvar(Reserva reserva) {
        if (reserva.getId() == null) {
            inicializarReserva(reserva);
        }
        return super.salvar(reserva);
    }

    @Override
    @Transactional
    public Reserva cancelarPorId(Long id) {
        Reserva reserva = buscarPorId(id);
        validarCancelamento(reserva);
        reserva.setStatusDaReserva(StatusDaReserva.CANCELADA);
        reserva = this.salvar(reserva);
        return reserva;
    }

    @Override
    public List<Reserva> buscarPorQuarto(Quarto quarto) {
        return reservaRepository.findByQuarto(quarto);
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

    @Override
    public Page<Reserva> listarPaginado(Pageable pageable) {
        return reservaRepository.buscarReservasAtivas(pageable);
    }

}
