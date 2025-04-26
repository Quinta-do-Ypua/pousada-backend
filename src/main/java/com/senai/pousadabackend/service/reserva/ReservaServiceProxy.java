package com.senai.pousadabackend.service.reserva;

import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.repository.ReservaRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ReservaServiceProxy extends BaseService<Reserva, Long, ReservaRepository> implements ReservaService {

    public ReservaServiceProxy(ReservaRepository repo) {
        super(repo);
    }

}
