package com.senai.pousadabackend.service.usuario;

import com.senai.pousadabackend.entity.Usuario;
import com.senai.pousadabackend.repository.UsuarioRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceProxy extends BaseService<Usuario, Long, UsuarioRepository> implements UsuarioService {

    public UsuarioServiceProxy(UsuarioRepository repo) {
        super(repo);
    }

}
