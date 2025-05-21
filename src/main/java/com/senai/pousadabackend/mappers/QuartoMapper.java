package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.QuartoDTO;
import com.senai.pousadabackend.entity.Quarto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class QuartoMapper implements BaseMapper<Quarto, QuartoDTO> {

    private final AmenidadeMapper amenidadeMapper;

    public QuartoMapper(AmenidadeMapper amenidadeMapper) {
        this.amenidadeMapper = amenidadeMapper;
    }

    @Override
    public QuartoDTO toDTO(Quarto quarto) {
        var quartoDto = QuartoDTO.builder()
                .id(quarto.getId())
                .nome(quarto.getNome())
                .capacidade(quarto.getCapacidade())
                .observacao(quarto.getObservacao())
                .qtdCamaCasal(quarto.getQtdCamaCasal())
                .qtdCamaSolteiro(quarto.getQtdCamaSolteiro())
                .valorDiaria(quarto.getValorDiaria())
                .build();

        if (quarto.getAmenidades() != null)
            quartoDto.setAmenidades(quarto.getAmenidades().stream().map(amenidadeMapper::toDTO).toList());

        return quartoDto;
    }

    @Override
    public Quarto toEntity(QuartoDTO quartoDTO) {
        var quarto = Quarto.builder()
                .id(quartoDTO.getId())
                .nome(quartoDTO.getNome())        .capacidade(quartoDTO.getCapacidade())
                .observacao(quartoDTO.getObservacao())
                .qtdCamaCasal(quartoDTO.getQtdCamaCasal())
                .qtdCamaSolteiro(quartoDTO.getQtdCamaSolteiro())
                .valorDiaria(quartoDTO.getValorDiaria())
                .build();

        if (quartoDTO.getAmenidades() != null)
            quarto.setAmenidades(quartoDTO.getAmenidades().stream().map(amenidadeMapper::toEntity).toList());

        return quarto;
    }
}
