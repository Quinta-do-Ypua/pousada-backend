package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.CupomDTO;
import com.senai.pousadabackend.entity.Cupom;
import org.springframework.stereotype.Component;

@Component
public class CupomMapper implements BaseMapper<Cupom, CupomDTO> {

    @Override
    public CupomDTO toDTO(Cupom cupom) {
        return CupomDTO.builder()
                .id(cupom.getId())
                .nome(cupom.getNome())
                .codigo(cupom.getCodigo())
                .dataDeInicio(cupom.getDataDeInicio())
                .dataDeVencimento(cupom.getDataDeVencimento())
                .porcentagemDeDesconto(cupom.getPorcentagemDeDesconto())
                .build();
    }

    @Override
    public Cupom toEntity(CupomDTO cupomDTO) {
        return Cupom.builder()
                .id(cupomDTO.getId())
                .nome(cupomDTO.getNome())
                .codigo(cupomDTO.getCodigo())
                .dataDeInicio(cupomDTO.getDataDeInicio())
                .dataDeVencimento(cupomDTO.getDataDeVencimento())
                .porcentagemDeDesconto(cupomDTO.getPorcentagemDeDesconto())
                .build();
    }

}
