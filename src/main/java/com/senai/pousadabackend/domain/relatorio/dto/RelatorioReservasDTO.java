package com.senai.pousadabackend.domain.relatorio.dto;

import com.senai.pousadabackend.domain.reserva.dto.ReservaDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class RelatorioReservasDTO {
    private List<ReservaDTO> reservas;
    private Long totalReservas;
    private Map<String, Long> reservasPorStatus;
    private BigDecimal faturamentoTotal;
    private BigDecimal ticketMedio;
    private BigDecimal totalDescontoCupons;
    private List<FaturamentoPorQuartoDTO> faturamentoPorQuarto;
}
