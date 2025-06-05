package com.senai.pousadabackend.domain.nota_fiscal.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemDto {

    private Long id;

    @NotBlank
    private String tipoItem;

    @NotNull
    private Long itemId;

    @NotBlank
    private BigDecimal valorUnitario;
}
