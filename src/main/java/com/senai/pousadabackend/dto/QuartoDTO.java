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

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A capacidade é obrigatória")
    private Integer capacidade;

    private Integer qtdCamaSolteiro;

    private Integer qtdCamaCasal;

    @NotNull(message = "O valor da diaria é obrigatório")
    private BigDecimal valorDiaria;

    private List<AmenidadeDto> amenidades;

    private String observacao;
}
