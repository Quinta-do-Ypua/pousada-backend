package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.dto.UsuarioDTO;
import com.senai.pousadabackend.service.BaseServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsuarioController extends BaseController<UsuarioDTO, Long> {

    public UsuarioController(@Qualifier("usuarioServiceProxy") BaseServiceInterface<UsuarioDTO, Long> baseServiceInterface) {
        super(baseServiceInterface);
    }
}
