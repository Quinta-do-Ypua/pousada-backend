package com.senai.pousadabackend.domain.configuracao;

import com.senai.pousadabackend.core.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TemaSistemaMapper implements BaseMapper<TemaSistema, TemaSistemaDTO> {

    @Override
    public TemaSistemaDTO toDTO(TemaSistema temaSistema) {
        if (temaSistema == null) {
            return null;
        }
        return TemaSistemaDTO.builder()
                .id(temaSistema.getId())
                .nomeEstabelecimento(temaSistema.getNomeEstabelecimento())
                .corPrimaria(temaSistema.getCorPrimaria())
                .corSecundaria(temaSistema.getCorSecundaria())
                .corDoTexto(temaSistema.getCorDoTexto())
                .urlLogo(temaSistema.getUrlLogo())
                .urlDaImagemPrincipal(temaSistema.getUrlDaImagemPrincipal())
                .build();
    }

    @Override
    public TemaSistema toEntity(TemaSistemaDTO dto) {
        if (dto == null) {
            return null;
        }
        return TemaSistema.builder()
                .id(dto.getId())
                .nomeEstabelecimento(dto.getNomeEstabelecimento())
                .corPrimaria(dto.getCorPrimaria())
                .corSecundaria(dto.getCorSecundaria())
                .corDoTexto(dto.getCorDoTexto())
                .urlLogo(dto.getUrlLogo())
                .urlDaImagemPrincipal(dto.getUrlDaImagemPrincipal())
                .build();
    }

    public List<TemaSistemaDTO> toDTOList(List<TemaSistema> temaSistemas) {
        if (temaSistemas == null) {
            return null;
        }
        return temaSistemas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(TemaSistemaDTO dto, TemaSistema temaSistema) {
        if (dto == null || temaSistema == null) {
            return;
        }
        if (dto.getNomeEstabelecimento() != null) {
            temaSistema.setNomeEstabelecimento(dto.getNomeEstabelecimento());
        }
        if (dto.getCorPrimaria() != null) {
            temaSistema.setCorPrimaria(dto.getCorPrimaria());
        }
        if (dto.getCorSecundaria() != null) {
            temaSistema.setCorSecundaria(dto.getCorSecundaria());
        }
        if (dto.getCorDoTexto() != null) {
            temaSistema.setCorDoTexto(dto.getCorDoTexto());
        }
        if (dto.getUrlLogo() != null) {
            temaSistema.setUrlLogo(dto.getUrlLogo());
        }
        if (dto.getUrlDaImagemPrincipal() != null) {
            temaSistema.setUrlDaImagemPrincipal(dto.getUrlDaImagemPrincipal());
        }
    }

}
