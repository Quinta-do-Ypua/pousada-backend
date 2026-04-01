package com.senai.pousadabackend.domain.usuario;

import com.senai.pousadabackend.core.base.BaseServiceInterface;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UsuarioService extends BaseServiceInterface<Usuario, Long> {

    Usuario buscarPorEmail(String email);

}
