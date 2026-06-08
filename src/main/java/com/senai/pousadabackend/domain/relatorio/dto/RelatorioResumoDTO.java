package com.senai.pousadabackend.domain.relatorio.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class RelatorioResumoDTO {
    private Long totalReservas;
    private Map<String, Long> reservasPorStatus;
    private BigDecimal faturamentoTotal;
    private Long reservasEsteMes;
    private BigDecimal faturamentoEsteMes;
    private Double taxaCancelamento;
}
