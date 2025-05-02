package com.senai.pousadabackend.service.reserva;

import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ReservaServiceProxy extends BaseService<Reserva, Long, ReservaRepository> implements ReservaService {

    private final ReservaService reservaService;
    public ReservaServiceProxy(ReservaRepository repo, @Qualifier("reservaServiceProxy") ReservaService reservaService) {
        super(repo);
        this.reservaService = reservaService;
    }

    @Override
    public Reserva cancelarPorId(Long id) {
        return reservaService.cancelarPorId(id);
    }
}
