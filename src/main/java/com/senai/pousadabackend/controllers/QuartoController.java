package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoDTO;
import com.senai.pousadabackend.domain.quarto.QuartoMapper;
import com.senai.pousadabackend.domain.quarto.service.QuartoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quartos")
public class QuartoController extends BaseController<Quarto, QuartoDTO, Long, QuartoMapper> {

    public QuartoController(QuartoMapper mapper,
                            @Qualifier("quartoServiceProxy") QuartoService quartoService) {
        super(mapper, quartoService);
    }

}
