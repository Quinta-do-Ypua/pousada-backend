package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.service.reserva.ReservaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservas")
public class ReservaController extends BaseController<Reserva, Long> {

    private final ReservaService reservaService;

    public ReservaController(@Qualifier("reservaServiceProxy") ReservaService reservaService) {
        super(reservaService);
        this.reservaService = reservaService;
    }

    @PatchMapping("/{id}/cancelar")
    public Reserva cancelarReserva(@PathVariable Long id) {
        return reservaService.cancelarPorId(id);
    }

}
