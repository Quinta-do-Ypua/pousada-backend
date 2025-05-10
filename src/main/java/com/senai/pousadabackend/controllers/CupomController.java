package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.dto.CupomDTO;
import com.senai.pousadabackend.entity.Cupom;
import com.senai.pousadabackend.mappers.CupomMapper;
import com.senai.pousadabackend.service.cupom.CupomService;
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
