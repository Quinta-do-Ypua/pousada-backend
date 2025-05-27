package com.senai.pousadabackend.domain.quarto;

import com.senai.pousadabackend.domain.amenidade.AmenidadeMapper;
import com.senai.pousadabackend.core.BaseMapper;
import org.springframework.stereotype.Component;

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

        if (quarto.getUrlImagens() != null && !quarto.getUrlImagens().isEmpty())
            quartoDto.setUrlImagens(quarto.getUrlImagens());

        return quartoDto;
    }

    @Override
    public Quarto toEntity(QuartoDTO quartoDTO) {
        var quarto = Quarto.builder()
                .id(quartoDTO.getId())
                .nome(quartoDTO.getNome())
                .capacidade(quartoDTO.getCapacidade())
                .observacao(quartoDTO.getObservacao())
                .qtdCamaCasal(quartoDTO.getQtdCamaCasal())
                .qtdCamaSolteiro(quartoDTO.getQtdCamaSolteiro())
                .valorDiaria(quartoDTO.getValorDiaria())
                .build();

        if (quartoDTO.getAmenidades() != null)
            quarto.setAmenidades(quartoDTO.getAmenidades().stream().map(amenidadeMapper::toEntity).toList());

        if (quartoDTO.getUrlImagens() != null && !quartoDTO.getUrlImagens().isEmpty())
            quarto.setUrlImagens(quartoDTO.getUrlImagens());

        return quarto;
    }
}
