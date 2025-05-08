package com.senai.pousadabackend.dto;

import com.senai.pousadabackend.entity.Endereco;
import com.senai.pousadabackend.entity.enums.Sexo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDTO {

    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private String celular;

    private Sexo sexo;

    private Endereco endereco;

}
