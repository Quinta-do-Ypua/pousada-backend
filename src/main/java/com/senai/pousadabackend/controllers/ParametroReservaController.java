package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.core.BaseServiceInterface;
import com.senai.pousadabackend.domain.parametro.ParametroReserva;
import com.senai.pousadabackend.domain.parametro.ParametroReservaDTO;
import com.senai.pousadabackend.domain.parametro.ParametroReservaMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parametros-reserva")
public class ParametroReservaController extends BaseController<ParametroReserva, ParametroReservaDTO, Long, ParametroReservaMapper> {

    public ParametroReservaController(ParametroReservaMapper mapper,
                                       BaseServiceInterface<ParametroReserva, Long> baseServiceInterface) {
        super(mapper, baseServiceInterface);
    }

}
