package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.dto.CupomDTO;
import com.senai.pousadabackend.service.BaseServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cupons")
public class CupomController extends BaseController<CupomDTO, Long> {

    public CupomController(@Qualifier("usuarioServiceProxy") BaseServiceInterface<CupomDTO, Long> baseServiceInterface) {
        super(baseServiceInterface);
    }
}
