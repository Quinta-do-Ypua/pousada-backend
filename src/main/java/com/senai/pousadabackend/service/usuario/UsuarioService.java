package com.senai.pousadabackend.service.usuario;

import com.senai.pousadabackend.entity.Usuario;
import com.senai.pousadabackend.service.BaseServiceInterface;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UsuarioService extends BaseServiceInterface<Usuario, Long> {
}
