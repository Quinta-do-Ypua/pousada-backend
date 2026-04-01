package com.senai.pousadabackend.domain.Imagem.configuracao;

import com.senai.pousadabackend.core.base.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class ImagemConfiguracaoMapper implements BaseMapper<ImagemConfiguracao, ImagemConfiguracaoDTO> {

    @Override
    public ImagemConfiguracaoDTO toDTO(ImagemConfiguracao imagemConfiguracao) {
        return ImagemConfiguracaoDTO.builder()
                .id(imagemConfiguracao.getId())
                .url(imagemConfiguracao.getUrl())
                .fileId(imagemConfiguracao.getFileId())
                .build();
    }

    @Override
    public ImagemConfiguracao toEntity(ImagemConfiguracaoDTO imagemConfiguracaoDTO) {
        return ImagemConfiguracao.builder()
                .id(imagemConfiguracaoDTO.getId())
                .url(imagemConfiguracaoDTO.getUrl())
                .fileId(imagemConfiguracaoDTO.getFileId())
                .build();
    }

}
