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

    @NotNull(message = "O item é obrigatório")
    private ItemDto item;

    @NotNull(message = "A quantidade dos itens é obrigatória")
    private Integer quantidade;

    @NotNull(message = "O valor unitário é obrigatório")
    private BigDecimal valorUnitario;

    @NotNull(message = "O valor total é obrigatório")
    private BigDecimal valorTotal;

}
