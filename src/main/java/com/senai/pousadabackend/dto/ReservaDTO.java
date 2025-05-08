package com.senai.pousadabackend.dto;

import com.senai.pousadabackend.entity.Cliente;
import com.senai.pousadabackend.entity.Complemento;
import com.senai.pousadabackend.entity.Quarto;
import com.senai.pousadabackend.entity.enums.StatusDaReserva;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReservaDTO {

    private Long id;

    private Quarto quarto;

    private BigDecimal valorTotalDoQuarto;

    private BigDecimal valorDaDiariaDoQuarto;

    private BigDecimal valorDaReserva;

    private BigDecimal valorComplementos;

    private StatusDaReserva status;

    private String observacao;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private Cliente cliente;

    private List<Complemento> complementos;

}
