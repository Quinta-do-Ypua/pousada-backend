package com.senai.pousadabackend.domain.temaSistema.dto;

import com.senai.pousadabackend.config.validation.GrupoValidacaoAlterar;
import com.senai.pousadabackend.config.validation.GrupoValidacaoInserir;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemaSistemaDTO {

    @Null(message = "O id deve estar vazio", groups = GrupoValidacaoInserir.class)
    @NotNull(message = "O id é obrigatório", groups = GrupoValidacaoAlterar.class)
    private Long id;

    private String nomeEstabelecimento;
    private String corPrimaria;
    private String corSecundaria;
    private String corDoTexto;
    private String urlLogo;
    private String urlDaImagemPrincipal;

}
