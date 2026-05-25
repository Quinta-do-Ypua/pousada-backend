package com.senai.pousadabackend.domain.tema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemaDTO {

    private Long id;
    private String buttonColor;
    private String titleColor;
    private String textColor;
    private String logo;

}