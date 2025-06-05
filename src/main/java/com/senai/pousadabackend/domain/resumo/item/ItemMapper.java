package com.senai.pousadabackend.domain.resumo.item;

import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemDto toDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .itemId(item.getItemId())
                .tipoItem(item.getTipoItem())
                .valorUnitario(item.getValorUnitario())
                .build();
    }

}
