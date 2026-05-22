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
                .primaryColor(tema.getPrimaryColor())
                .secondaryColor(tema.getSecondaryColor())
                .textColor(tema.getTextColor())
                .grayBg(tema.getGrayBg())
                .graySecondaryBg(tema.getGraySecondaryBg())
                .logo(tema.getLogo())
                .loginImage(tema.getLoginImage())
                .build();
    }

    @Override
    public Tema toEntity(TemaDTO temaDTO) {
        return Tema.builder()
                .id(temaDTO.getId())
                .primaryColor(temaDTO.getPrimaryColor())
                .secondaryColor(temaDTO.getSecondaryColor())
                .textColor(temaDTO.getTextColor())
                .grayBg(temaDTO.getGrayBg())
                .graySecondaryBg(temaDTO.getGraySecondaryBg())
                .logo(temaDTO.getLogo())
                .loginImage(temaDTO.getLoginImage())
                .build();
    }

}