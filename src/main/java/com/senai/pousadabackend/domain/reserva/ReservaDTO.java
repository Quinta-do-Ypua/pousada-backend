package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.domain.cliente.ClienteDTO;
import com.senai.pousadabackend.domain.complemento.ComplementoDTO;
import com.senai.pousadabackend.domain.quarto.QuartoDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReservaDTO {

    private Long id;

    @NotNull
    private QuartoDTO quarto;

    private BigDecimal valorDaReserva;

    private StatusDaReserva statusDaReserva;

    private String observacao;

    @NotNull
    private LocalDateTime checkIn;

    @NotNull
    private LocalDateTime checkOut;

    @NotNull
    private ClienteDTO cliente;

    private List<ComplementoDTO> complementos;

}
