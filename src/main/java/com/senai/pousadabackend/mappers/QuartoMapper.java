package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.QuartoDTO;
import com.senai.pousadabackend.entity.Quarto;
import org.springframework.stereotype.Component;

@Component
public class QuartoMapper implements BaseMapper<Quarto, QuartoDTO> {


    @Override
    public QuartoDTO toDTO(Quarto quarto) {
        return QuartoDTO.builder()
                .id(quarto.getId())
                .nome(quarto.getNome())
                .complementos(quarto.getComplementos())
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
                .complementos(quartoDTO.getComplementos())
                .capacidade(quartoDTO.getCapacidade())
                .observacao(quartoDTO.getObservacao())
                .qtdCamaCasal(quartoDTO.getQtdCamaCasal())
                .qtdCamaSolteiro(quartoDTO.getQtdCamaSolteiro())
                .valorDiaria(quartoDTO.getValorDiaria())
                .build();
    }
}
