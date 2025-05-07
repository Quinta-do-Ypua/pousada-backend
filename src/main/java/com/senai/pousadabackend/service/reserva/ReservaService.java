package com.senai.pousadabackend.service.reserva;

import com.senai.pousadabackend.entity.Quarto;
import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.service.BaseServiceInterface;

import java.time.LocalDateTime;

public interface ReservaService extends BaseServiceInterface<Reserva, Long> {

    Reserva cancelarPorId(Long id);

}
