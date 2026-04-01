package com.senai.pousadabackend.domain.usuario;

import com.senai.pousadabackend.core.base.BaseMapper;
import com.senai.pousadabackend.domain.usuario.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper implements BaseMapper<Usuario, UsuarioDTO> {

    @Override
    public UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .build();
    }

    @Override
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .id(usuarioDTO.getId())
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .roles(usuarioDTO.getRoles())
                .build();
    }

}
