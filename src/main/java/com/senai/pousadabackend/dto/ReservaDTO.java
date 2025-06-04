package com.senai.pousadabackend.dto;

import com.senai.pousadabackend.entity.Cliente;
import com.senai.pousadabackend.entity.Quarto;
import com.senai.pousadabackend.entity.enums.StatusDaReserva;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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

    @Length(max = 1000)
    private String observacao;

    @NotNull
    private LocalDateTime checkIn;

    @NotNull
    private LocalDateTime checkOut;

    @NotNull
    private ClienteDTO cliente;

    private List<ComplementoDTO> complementos;

}
