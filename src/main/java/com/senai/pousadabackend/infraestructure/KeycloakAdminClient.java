package com.senai.pousadabackend.infraestructure;

import com.senai.pousadabackend.domain.auth.dto.KeycloakRoleResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(
        name = "keycloak-admin",
        url = "${keycloak.admin-server-url}"
)
public interface KeycloakAdminClient {

    @GetMapping("/clients/{clientUuid}/roles")
    List<KeycloakRoleResponseDTO> getClientRoles(
            @RequestHeader("Authorization") String token,
            @PathVariable("clientUuid") String clientUuid
    );

}
