package com.senai.pousadabackend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ComplementoDTO {

    private Long id;

    private String nome;

    private BigDecimal valor;

    private String descricao;
}
