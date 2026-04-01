package com.senai.pousadabackend.infraestructure;

import com.senai.pousadabackend.domain.auth.dto.KeycloakClientResponseDTO;
import com.senai.pousadabackend.domain.auth.dto.KeycloakRoleResponseDTO;
import com.senai.pousadabackend.domain.auth.dto.KeycloakUserRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "keycloak-admin",
        url = "${keycloak.admin-server-url}"
)
public interface KeycloakAdminClient {

    @PostMapping("/users")
    ResponseEntity<Void> createUser(
            @RequestHeader("Authorization") String token,
            @RequestBody KeycloakUserRequestDTO user
    );

    @GetMapping("/clients")
    List<KeycloakClientResponseDTO> getClients(
            @RequestHeader("Authorization") String token,
            @RequestParam("clientId") String clientId
    );

    @GetMapping("/clients/{clientUuid}/roles")
    List<KeycloakRoleResponseDTO> getClientRoles(
            @RequestHeader("Authorization") String token,
            @PathVariable("clientUuid") String clientUuid
    );

    @PostMapping("/users/{userId}/role-mappings/clients/{clientUuid}")
    ResponseEntity<Void> assignClientRoles(
            @RequestHeader("Authorization") String token,
            @PathVariable("userId") String userId,
            @PathVariable("clientUuid") String clientUuid,
            @RequestBody List<Map<String, String>> roles
    );
}
