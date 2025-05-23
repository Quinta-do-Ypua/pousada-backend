package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.NotaFiscalItemReduzidoDto;
import com.senai.pousadabackend.entity.NotaFiscalItem;
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
