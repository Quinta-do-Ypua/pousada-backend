package com.senai.pousadabackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class QuartoDTO {

    private Long id;

    @NotBlank
    private String numero;

    @NotNull
    private Integer capacidade;

    @NotNull
    private BigDecimal valorDiaria;

}
