package com.senai.pousadabackend.domain.nota_fiscal.item_nf;

import com.senai.pousadabackend.domain.nota_fiscal.item.ItemMapper;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalItemReduzidoMapper  {

    private final ItemMapper itemMapper;

    public NotaFiscalItemReduzidoMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public NotaFiscalItemReduzidoDto toDTO(NotaFiscalItem notaFiscalItem) {
        return NotaFiscalItemReduzidoDto.builder()
                .id(notaFiscalItem.getId())
                .item(itemMapper.toDto(notaFiscalItem.getItem()))
                .quantidade(notaFiscalItem.getQuantidade())
                .valorUnitario(notaFiscalItem.getValorUnitario())
                .valorTotal(notaFiscalItem.getValorTotal())
                .build();
    }

}
