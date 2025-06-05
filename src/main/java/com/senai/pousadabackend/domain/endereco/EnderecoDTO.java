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

    @NotBlank
    private String bairro;

    @NotBlank
    private String numero;

    private String complemento;

}
