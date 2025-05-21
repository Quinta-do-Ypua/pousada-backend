package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.QuartoDTO;
import com.senai.pousadabackend.entity.Quarto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class QuartoMapper implements BaseMapper<Quarto, QuartoDTO> {

    private final AmenidadeMapper amenidadeMapper;

    public QuartoMapper(AmenidadeMapper amenidadeMapper) {
        this.amenidadeMapper = amenidadeMapper;
    }

    @Override
    public QuartoDTO toDTO(Quarto quarto) {
        return QuartoDTO.builder()
                .id(quarto.getId())
                .nome(quarto.getNome())
                .amenidades(quarto.getAmenidades().stream().map(amenidadeMapper::toDTO).toList())
                .capacidade(quarto.getCapacidade())
                .observacao(quarto.getObservacao())
                .qtdCamaCasal(quarto.getQtdCamaCasal())
                .qtdCamaSolteiro(quarto.getQtdCamaSolteiro())
                .valorDiaria(quarto.getValorDiaria())
                .build();
    }

    @Override
    public Quarto toEntity(QuartoDTO quartoDTO) {
        return Quarto.builder()
                .id(quartoDTO.getId())
                .nome(quartoDTO.getNome())
                .amenidades(quartoDTO.getAmenidades().stream().map(amenidadeMapper::toEntity).toList())
                .capacidade(quartoDTO.getCapacidade())
                .observacao(quartoDTO.getObservacao())
                .qtdCamaCasal(quartoDTO.getQtdCamaCasal())
                .qtdCamaSolteiro(quartoDTO.getQtdCamaSolteiro())
                .valorDiaria(quartoDTO.getValorDiaria())
                .build();
    }
}
