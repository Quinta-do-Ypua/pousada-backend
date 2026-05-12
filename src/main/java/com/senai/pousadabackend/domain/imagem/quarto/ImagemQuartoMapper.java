package com.senai.pousadabackend.domain.imagem.quarto;

import com.senai.pousadabackend.core.base.BaseMapper;
import com.senai.pousadabackend.domain.imagem.quarto.dto.ImagemQuartoDTO;
import org.springframework.stereotype.Component;

@Component
public class ImagemQuartoMapper implements BaseMapper<ImagemQuarto, ImagemQuartoDTO> {

    @Override
    public ImagemQuartoDTO toDTO(ImagemQuarto imagemQuarto) {
        return ImagemQuartoDTO.builder()
                .id(imagemQuarto.getId())
                .url(imagemQuarto.getUrl())
                .fileId(imagemQuarto.getFileId())
                .build();
    }

    @Override
    public ImagemQuarto toEntity(ImagemQuartoDTO imagemQuartoDTO) {
        return ImagemQuarto.builder()
                .id(imagemQuartoDTO.getId())
                .url(imagemQuartoDTO.getUrl())
                .fileId(imagemQuartoDTO.getFileId())
                .build();
    }
}
