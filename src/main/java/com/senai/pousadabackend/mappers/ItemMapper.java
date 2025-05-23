package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.ItemDto;
import com.senai.pousadabackend.entity.Item;
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
