package com.senai.pousadabackend.domain.usuario.service;

import com.senai.pousadabackend.domain.usuario.Usuario;
import com.senai.pousadabackend.core.BaseServiceInterface;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UsuarioService extends BaseServiceInterface<Usuario, Long> {
}
