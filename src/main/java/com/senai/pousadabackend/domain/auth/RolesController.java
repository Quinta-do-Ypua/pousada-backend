package com.senai.pousadabackend.domain.auth;

import com.senai.pousadabackend.domain.auth.dto.KeycloakRoleResponseDTO;
import com.senai.pousadabackend.domain.auth.dto.KeycloakTokenResponseDTO;
import com.senai.pousadabackend.infraestructure.KeycloakAdminClient;
import com.senai.pousadabackend.infraestructure.KeycloakTokenClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final KeycloakTokenClient tokenClient;
    private final KeycloakAdminClient adminClient;

    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

    public RolesController(KeycloakTokenClient tokenClient, KeycloakAdminClient adminClient) {
        this.tokenClient = tokenClient;
        this.adminClient = adminClient;
    }

    @GetMapping("/keycloak")
    public ResponseEntity<List<KeycloakRoleResponseDTO>> getClientRoles() {

        String token = getClientToken();

        List<KeycloakRoleResponseDTO> allAvailableRoles = adminClient.getClientRoles("Bearer " + token, clientId);
        return ResponseEntity.ok(allAvailableRoles);
    }

    private String getClientToken() {
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("client_id", clientId);
        formParams.add("client_secret", clientSecret);
        formParams.add("grant_type", GRANT_TYPE_CLIENT_CREDENTIALS);

        KeycloakTokenResponseDTO tokenResponse = tokenClient.getToken(formParams);
        return tokenResponse.getAccessToken();
    }
}
