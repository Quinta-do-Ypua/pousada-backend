package com.senai.pousadabackend.domain.tema;

import com.senai.pousadabackend.core.base.BaseMapper;
import com.senai.pousadabackend.domain.tema.dto.TemaDTO;
import org.springframework.stereotype.Component;

@Component
public class TemaMapper implements BaseMapper<Tema, TemaDTO> {

    @Override
    public TemaDTO toDTO(Tema tema) {
        return TemaDTO.builder()
                .id(tema.getId())
                .buttonColor(tema.getButtonColor())
                .titleColor(tema.getTitleColor())
                .textColor(tema.getTextColor())
                .logo(tema.getLogo())
                .build();
    }

    @Override
    public Tema toEntity(TemaDTO temaDTO) {
        return Tema.builder()
                .id(temaDTO.getId())
                .buttonColor(temaDTO.getButtonColor())
                .titleColor(temaDTO.getTitleColor())
                .textColor(temaDTO.getTextColor())
                .logo(temaDTO.getLogo())
                .build();
    }

}