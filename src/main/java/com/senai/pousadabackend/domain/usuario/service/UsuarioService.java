package com.senai.pousadabackend.domain.usuario.service;

import com.senai.pousadabackend.core.BaseServiceInterface;
import com.senai.pousadabackend.domain.usuario.Usuario;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UsuarioService extends BaseServiceInterface<Usuario, Long> {

    Usuario buscarPorEmail(String email);

}
