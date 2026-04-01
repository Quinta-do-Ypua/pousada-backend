package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.cliente.dto.ClienteDTO;
import com.senai.pousadabackend.domain.complemento.dto.ComplementoDTO;
import com.senai.pousadabackend.domain.quarto.dto.QuartoDTO;
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
