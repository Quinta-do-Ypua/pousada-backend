package com.senai.pousadabackend.service.reserva;

import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.repository.ReservaRepository;
import com.senai.pousadabackend.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ReservaServiceImpl extends BaseServiceImpl<Reserva, Long, ReservaRepository> implements ReservaService {

    public ReservaServiceImpl(ReservaRepository repo) {
        super(repo);
    }

}
