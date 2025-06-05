package com.senai.pousadabackend.domain.nota_fiscal.item_nf;

import com.senai.pousadabackend.domain.nota_fiscal.item.ItemDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class NotaFiscalItemReduzidoDto {

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
