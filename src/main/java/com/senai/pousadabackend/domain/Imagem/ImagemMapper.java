package com.senai.pousadabackend.domain.Imagem;

import com.senai.pousadabackend.core.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class ImagemMapper implements BaseMapper<ImagemQuarto, ImagemQuartoDTO> {

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
