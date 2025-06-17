package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.domain.complemento.ComplementoDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResumidaDto {

    private Long id;

    @NotNull
    private Long quartoId;

    private BigDecimal valorDaReserva;

    private StatusDaReserva statusDaReserva;

    @Length(max = 1000)
    private String observacao;

    @NotNull
    private LocalDateTime checkIn;

    @NotNull
    private LocalDateTime checkOut;

    @NotNull
    private Long clienteId;

    private List<ComplementoDTO> complementos;

}
