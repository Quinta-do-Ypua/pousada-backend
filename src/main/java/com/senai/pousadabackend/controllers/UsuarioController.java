package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.entity.Usuario;
import com.senai.pousadabackend.service.BaseServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsuarioController extends BaseController<Usuario, Long> {

    public UsuarioController(@Qualifier("usuarioServiceProxy") BaseServiceInterface<Usuario, Long> baseServiceInterface) {
        super(baseServiceInterface);
    }
}
