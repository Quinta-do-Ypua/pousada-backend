package com.senai.pousadabackend.domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeycloakTokenRequestDTO {

    private String clientId;
    private String clientSecret;
    private String grantType;
}
