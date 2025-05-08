package com.senai.pousadabackend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class QuartoDTO {

    private Long id;

    private String numero;

    private Integer capacidade;

    private BigDecimal valorDiaria;

}
