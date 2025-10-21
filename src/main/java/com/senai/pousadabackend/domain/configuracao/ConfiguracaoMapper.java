package com.senai.pousadabackend.domain.configuracao;

import com.senai.pousadabackend.core.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracaoMapper implements BaseMapper<Configuracao, ConfiguracaoDTO> {

    @Override
    public ConfiguracaoDTO toDTO(Configuracao configuracao) {
        return ConfiguracaoDTO.builder()
                .id(configuracao.getId())
                .corPrimaria(configuracao.getCorPrimaria())
                .corDoTexto(configuracao.getCorDoTexto())
                .urlLogo(configuracao.getUrlLogo())
                .urlDaImagemPrincipal(configuracao.getUrlDaImagemPrincipal())
                .build();
    }

    @Override
    public Configuracao toEntity(ConfiguracaoDTO configuracaoDTO) {
        return Configuracao.builder()
                .id(configuracaoDTO.getId())
                .corPrimaria(configuracaoDTO.getCorPrimaria())
                .corDoTexto(configuracaoDTO.getCorDoTexto())
                .urlLogo(configuracaoDTO.getUrlLogo())
                .urlDaImagemPrincipal(configuracaoDTO.getUrlDaImagemPrincipal())
                .build();
    }

}
