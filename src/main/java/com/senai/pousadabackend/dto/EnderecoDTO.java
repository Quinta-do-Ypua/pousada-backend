package com.senai.pousadabackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoDTO {

    private Long id;

    private String cidade;

    private String estado;

    private String rua;

    private String cep;

    private String bairro;

    private String numero;

    private String complemento;
}
