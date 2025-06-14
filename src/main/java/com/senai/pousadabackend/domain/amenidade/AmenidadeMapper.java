package com.senai.pousadabackend.domain.amenidade;

import com.senai.pousadabackend.core.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class AmenidadeMapper implements BaseMapper<Amenidade, AmenidadeDto> {

    @Override
    public AmenidadeDto toDTO(Amenidade amenidade) {
        return AmenidadeDto.builder()
                .id(amenidade.getId())
                .nome(amenidade.getNome())
                .icone(amenidade.getIcone())
                .build();
    }

    @Override
    public Amenidade toEntity(AmenidadeDto amenidadeDto) {
        return Amenidade.builder()
                .id(amenidadeDto.getId())
                .nome(amenidadeDto.getNome())
                .icone(amenidadeDto.getIcone())
                .build();
    }
}
