package com.senai.pousadabackend.domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeycloakClientResponseDTO {

    private String id;
    private String clientId;
    private String name;
}
