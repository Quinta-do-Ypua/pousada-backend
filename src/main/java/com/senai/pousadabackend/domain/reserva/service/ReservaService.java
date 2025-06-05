package com.senai.pousadabackend.domain.reserva.service;

import com.senai.pousadabackend.core.BaseServiceInterface;
import com.senai.pousadabackend.domain.reserva.Reserva;

public interface ReservaService extends BaseServiceInterface<Reserva, Long> {

    Reserva cancelarPorId(Long id);

}
