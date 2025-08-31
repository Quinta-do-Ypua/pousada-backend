package com.senai.pousadabackend.domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeycloakRoleResponseDTO {

    private String id;
    private String name;
    private String description;
}
