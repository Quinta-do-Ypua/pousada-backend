package com.senai.pousadabackend.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoDTO {

    private Long id;

    @NotBlank
    private String cidade;

    @NotBlank
    private String estado;

    @NotBlank
    private String rua;

    @NotBlank
    private String cep;

    private String bairro;

    private String numero;

    private String complemento;
}
