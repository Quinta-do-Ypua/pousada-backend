package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.cupom.CupomDTO;
import com.senai.pousadabackend.domain.cupom.Cupom;
import com.senai.pousadabackend.domain.cupom.CupomMapper;
import com.senai.pousadabackend.domain.cupom.service.CupomService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cupons")
public class CupomController extends BaseController<Cupom, CupomDTO, Long, CupomMapper> {

    public CupomController(CupomMapper mapper,
                           @Qualifier("cupomServiceProxy") CupomService cupomService) {
        super(mapper, cupomService);
    }

}
