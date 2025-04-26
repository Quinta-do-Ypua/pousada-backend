package com.senai.pousadabackend.dto;

import com.senai.pousadabackend.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UsuarioDTO {

    private Long id;

    private String nome;

    private String email;

    private String senha;

    private Set<Role> permissoes;
}
