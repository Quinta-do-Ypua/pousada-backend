package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.domain.reserva.ReservaDTO;
import com.senai.pousadabackend.domain.reserva.ReservaMapper;
import com.senai.pousadabackend.domain.reserva.service.ReservaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservas")
public class ReservaController extends BaseController<Reserva, ReservaDTO, Long, ReservaMapper> {

    private final ReservaService reservaService;

    public ReservaController(ReservaMapper mapper,
                             @Qualifier("reservaServiceProxy") ReservaService reservaService) {
        super(mapper, reservaService);
        this.reservaService = reservaService;
    }

    @PatchMapping("/{id}/cancelar")
    public Reserva cancelarReserva(@PathVariable Long id) {
        return reservaService.cancelarPorId(id);
    }

}
