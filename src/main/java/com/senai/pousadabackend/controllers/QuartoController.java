package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.entity.Quarto;
import com.senai.pousadabackend.service.BaseServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quartos")
public class QuartoController extends BaseController<Quarto, Long> {

    public QuartoController(@Qualifier("quartoServiceImpl") BaseServiceInterface<Quarto, Long> baseServiceInterface) {
        super(baseServiceInterface);
    }

}
