package com.senai.pousadabackend.domain.reserva.service;

import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.core.BaseServiceInterface;

public interface ReservaService extends BaseServiceInterface<Reserva, Long> {

    Reserva cancelarPorId(Long id);

}
