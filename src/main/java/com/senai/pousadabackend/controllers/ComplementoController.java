package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.dto.ComplementoDTO;
import com.senai.pousadabackend.dto.UsuarioDTO;
import com.senai.pousadabackend.service.BaseServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("complementos")
public class ComplementoController extends BaseController<ComplementoDTO, Long> {

    public ComplementoController(@Qualifier("complementoServiceProxy") BaseServiceInterface<ComplementoDTO, Long> baseServiceInterface) {
        super(baseServiceInterface);
    }
}
