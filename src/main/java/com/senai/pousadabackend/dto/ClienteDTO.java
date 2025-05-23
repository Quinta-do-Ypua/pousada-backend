package com.senai.pousadabackend.dto;

import com.senai.pousadabackend.entity.enums.Sexo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@Builder
public class ClienteDTO {

    private Long id;

    @NotBlank
    private String nome;

    @NotBlank(message = "O cpf é obrigatório")
    @CPF(message = "O cpf deve ser valido")
    private String cpf;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String celular;

    @NotNull
    private Sexo sexo;

    @NotNull
    private LocalDate dataDeNascimento;

    @NotNull
    private EnderecoDTO endereco;

}
