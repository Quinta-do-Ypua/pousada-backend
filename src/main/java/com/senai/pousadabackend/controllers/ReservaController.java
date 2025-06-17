package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.reserva.*;
import com.senai.pousadabackend.domain.reserva.service.ReservaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaResumidaMapper reservaResumidaMapper;
    private final ReservaMapper reservaMapper;

    public ReservaController(@Qualifier("reservaServiceImpl") ReservaService reservaService, ReservaResumidaMapper reservaResumidaMapper, ReservaMapper reservaMapper) {
        this.reservaService = reservaService;
        this.reservaResumidaMapper = reservaResumidaMapper;
        this.reservaMapper = reservaMapper;
    }

    @PostMapping
    public ReservaDTO cadastrar(@RequestBody ReservaResumidaDto reservaResumidaDto) {
        return reservaMapper.toDTO(reservaService.salvar(reservaResumidaMapper.toReserva(reservaResumidaDto)));
    }

    @PatchMapping("/{id}/cancelar")
    public Reserva cancelarReserva(@PathVariable Long id) {
        return reservaService.cancelarPorId(id);
    }

}
