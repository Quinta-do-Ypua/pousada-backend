package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.core.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class ComplementoMapper implements BaseMapper<Complemento, ComplementoDTO> {

    @Override
    public ComplementoDTO toDTO(Complemento complemento) {
        return ComplementoDTO.builder()
                .id(complemento.getId())
                .descricao(complemento.getDescricao())
                .nome(complemento.getNome())
                .valor(complemento.getValor())
                .build();
    }

    @Override
    public Complemento toEntity(ComplementoDTO complementoDTO) {
        return Complemento.builder()
                .id(complementoDTO.getId())
                .descricao(complementoDTO.getDescricao())
                .nome(complementoDTO.getNome())
                .valor(complementoDTO.getValor())
                .build();
    }

}
