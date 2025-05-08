package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.UsuarioDTO;
import com.senai.pousadabackend.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper implements BaseMapper<Usuario, UsuarioDTO> {

    @Override
    public UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .build();
    }

    @Override
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .id(usuarioDTO.getId())
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .build();
    }

}
