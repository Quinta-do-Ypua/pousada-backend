package com.senai.pousadabackend.domain.resumo.itemnf;

import com.senai.pousadabackend.domain.resumo.item.ItemDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ResumoReservaItemReduzidoDto {

    private Long id;

    @NotNull
    private ItemDto item;

    @NotNull
    private Integer quantidade;

    @NotNull
    private BigDecimal valorUnitario;

    @NotNull
    private BigDecimal valorTotal;

}
