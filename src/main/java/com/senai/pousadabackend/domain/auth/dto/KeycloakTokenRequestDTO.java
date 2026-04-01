package com.senai.pousadabackend.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeycloakTokenRequestDTO {

    private String clientId;
    private String clientSecret;
    private String grantType;
}
