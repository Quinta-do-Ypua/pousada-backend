package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.entity.Cupom;
import com.senai.pousadabackend.service.BaseServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cupons")
public class CupomController extends BaseController<Cupom, Long> {

    public CupomController(@Qualifier("cupomServiceProxy") BaseServiceInterface<Cupom, Long> baseServiceInterface) {
        super(baseServiceInterface);
    }
}
