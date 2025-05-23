package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.usuario.UsuarioDTO;
import com.senai.pousadabackend.domain.usuario.Usuario;
import com.senai.pousadabackend.domain.usuario.UsuarioMapper;
import com.senai.pousadabackend.domain.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends BaseController<Usuario, UsuarioDTO, Long, UsuarioMapper> {

    public UsuarioController(UsuarioMapper mapper,
                             @Qualifier("usuarioServiceProxy") UsuarioService usuarioService) {
        super(mapper, usuarioService);
    }

}
