package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.service.BaseServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservas")
public class ReservaController extends BaseController<Reserva, Long> {

    public ReservaController(@Qualifier("reservaServiceImpl") BaseServiceInterface<Reserva, Long> baseServiceInterface) {
        super(baseServiceInterface);
    }

}
