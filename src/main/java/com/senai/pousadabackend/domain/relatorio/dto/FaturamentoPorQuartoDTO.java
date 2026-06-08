package com.senai.pousadabackend.domain.relatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaturamentoPorQuartoDTO {
    private String quartoNome;
    private BigDecimal faturamento;
}
