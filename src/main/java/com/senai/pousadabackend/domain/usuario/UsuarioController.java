package com.senai.pousadabackend.domain.usuario;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.domain.usuario.dto.UsuarioDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends BaseController<Usuario, UsuarioDTO, Long, UsuarioMapper> {

    public UsuarioController(UsuarioMapper mapper,
                             UsuarioService usuarioService) {
        super(mapper, usuarioService);
    }

}
