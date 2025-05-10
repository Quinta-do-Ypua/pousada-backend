package com.senai.pousadabackend.dto;

import com.senai.pousadabackend.config.validation.GrupoValidacaoAlterar;
import com.senai.pousadabackend.config.validation.GrupoValidacaoInserir;
import com.senai.pousadabackend.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UsuarioDTO {

    @Null(message = "O id deve estar vazio", groups = GrupoValidacaoInserir.class)
    @NotNull(message = "O id é obrigatório", groups = GrupoValidacaoAlterar.class)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Size(max = 50, message = "O email deve ter no máximo 100 caracteres")
    @Email(message = "O email deve conter o formato 'exemplo@dominio.com'")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    @NotBlank(message = "O status é obrigatório")
    private String status;

    @NotEmpty(message = "É necessário vincular permissões ao usuário")
    private Set<Role> permissoes;
}
