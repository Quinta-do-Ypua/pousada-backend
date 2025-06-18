package com.senai.pousadabackend.domain.reserva.service;

import com.senai.pousadabackend.core.BaseServiceInterface;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.reserva.Reserva;

import java.util.List;

public interface ReservaService extends BaseServiceInterface<Reserva, Long> {

    Reserva cancelarPorId(Long id);

    List<Reserva> buscarPorQuarto(Quarto quarto);

}
