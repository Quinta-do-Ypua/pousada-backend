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
    private String primaryColor;
    private String secondaryColor;
    private String textColor;
    private String grayBg;
    private String graySecondaryBg;
    private String logo;
    private String loginImage;

}