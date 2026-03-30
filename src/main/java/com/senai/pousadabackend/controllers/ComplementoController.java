package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.complemento.Complemento;
import com.senai.pousadabackend.domain.complemento.ComplementoDTO;
import com.senai.pousadabackend.domain.complemento.ComplementoMapper;
import com.senai.pousadabackend.domain.complemento.ComplementoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("complementos")
public class ComplementoController extends BaseController<Complemento, ComplementoDTO, Long, ComplementoMapper> {

    public ComplementoController(ComplementoMapper mapper,
                                 ComplementoService complementoService) {
        super(mapper, complementoService);
    }
}
