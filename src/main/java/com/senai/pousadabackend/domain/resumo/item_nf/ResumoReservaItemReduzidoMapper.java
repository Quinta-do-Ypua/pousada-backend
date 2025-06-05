package com.senai.pousadabackend.domain.resumo.item_nf;

import com.senai.pousadabackend.domain.resumo.item.ItemMapper;
import org.springframework.stereotype.Component;

@Component
public class ResumoReservaItemReduzidoMapper {

    private final ItemMapper itemMapper;

    public ResumoReservaItemReduzidoMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public ResumoReservaItemReduzidoDto toDTO(ResumoReservaItem resumoReservaItem) {
        return ResumoReservaItemReduzidoDto.builder()
                .id(resumoReservaItem.getId())
                .item(itemMapper.toDto(resumoReservaItem.getItem()))
                .quantidade(resumoReservaItem.getQuantidade())
                .valorUnitario(resumoReservaItem.getValorUnitario())
                .valorTotal(resumoReservaItem.getValorTotal())
                .build();
    }

}
