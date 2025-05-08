package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.dto.UsuarioDTO;
import com.senai.pousadabackend.entity.Usuario;
import com.senai.pousadabackend.mappers.UsuarioMapper;
import com.senai.pousadabackend.service.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsuarioController extends BaseController<Usuario, UsuarioDTO, Long, UsuarioMapper> {


    public UsuarioController(UsuarioMapper mapper,
                             @Qualifier("usuarioServiceProxy") UsuarioService usuarioService) {
        super(mapper, usuarioService);
    }

}
