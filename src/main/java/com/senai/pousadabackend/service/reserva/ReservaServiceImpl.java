package com.senai.pousadabackend.service.reserva;

import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.entity.enums.StatusDaReserva;
import com.senai.pousadabackend.exceptions.CancelamentoDeReservaConcluidaException;
import com.senai.pousadabackend.repository.ReservaRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ReservaServiceImpl extends BaseService<Reserva, Long, ReservaRepository> implements ReservaService {

    public ReservaServiceImpl(ReservaRepository repo) {
        super(repo);
    }

    @Override
    public Reserva cancelarPorId(Long id) {
        Reserva reserva = buscarPorId(id);
        checarSeReservaPodeCancelar(reserva);
        reserva.setStatus(StatusDaReserva.Cancelada);
        this.salvar(reserva);
        return reserva;
    }

    private void checarSeReservaPodeCancelar(Reserva reserva) {
        if (reserva.getStatus().equals(StatusDaReserva.Concluida)
                || reserva.getStatus().equals(StatusDaReserva.Fechada))
            throw new CancelamentoDeReservaConcluidaException();
    }
}
