package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.core.BaseServiceInterface;
import com.senai.pousadabackend.domain.configuracao.TemaSistema;
import com.senai.pousadabackend.domain.configuracao.TemaSistemaDTO;
import com.senai.pousadabackend.domain.configuracao.TemaSistemaMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tema")
public class TemaSistemaController extends BaseController<TemaSistema, TemaSistemaDTO, Long, TemaSistemaMapper> {

    public TemaSistemaController(TemaSistemaMapper mapper,
                                  BaseServiceInterface<TemaSistema, Long> baseServiceInterface) {
        super(mapper, baseServiceInterface);
    }

}
