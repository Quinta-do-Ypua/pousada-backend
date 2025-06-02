package com.senai.pousadabackend.dto;

import com.senai.pousadabackend.entity.Complemento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class QuartoDTO {

    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    private Integer capacidade;

    private Integer qtdCamaSolteiro;

    private Integer qtdCamaCasal;

    @NotNull
    private BigDecimal valorDiaria;

    private List<AmenidadeDto> amenidades;

    private String observacao;
}
