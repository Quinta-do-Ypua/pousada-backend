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
                .numero(quarto.getNumero())
                .capacidade(quarto.getCapacidade())
                .valorDiaria(quarto.getValorDiaria())
                .build();
    }

    @Override
    public Quarto toEntity(QuartoDTO quartoDTO) {
        return Quarto.builder()
                .id(quartoDTO.getId())
                .numero(quartoDTO.getNumero())
                .capacidade(quartoDTO.getCapacidade())
                .valorDiaria(quartoDTO.getValorDiaria())
                .build();
    }
}
