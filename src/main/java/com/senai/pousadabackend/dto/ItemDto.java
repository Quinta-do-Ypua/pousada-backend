package com.senai.pousadabackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemDto {

    private Long id;

    @NotBlank(message = "O tipo do item é obrigatório")
    private String tipoItem;

    @NotNull(message = "O id do item é obrigatório")
    private Long itemId;

    @NotBlank(message = "O valor unitário é obrigatório")
    private BigDecimal valorUnitario;
}
