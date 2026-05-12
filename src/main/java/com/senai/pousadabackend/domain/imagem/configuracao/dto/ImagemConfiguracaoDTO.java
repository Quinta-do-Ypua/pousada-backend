package com.senai.pousadabackend.domain.imagem.configuracao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImagemConfiguracaoDTO {

    private Long id;

    @NotBlank(message = "O url é obrigatório")
    private String url;

    @NotNull(message = "O file id é obrigatório")
    private String fileId;

}
