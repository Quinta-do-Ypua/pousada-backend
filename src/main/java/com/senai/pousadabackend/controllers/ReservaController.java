package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.dto.ReservaDTO;
import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.mappers.ReservaMapper;
import com.senai.pousadabackend.service.reserva.ReservaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
