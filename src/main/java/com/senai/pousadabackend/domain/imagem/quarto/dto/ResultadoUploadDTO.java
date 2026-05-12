package com.senai.pousadabackend.domain.imagem.quarto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoUploadDTO {

    private String objectName;
    private String url;
}
