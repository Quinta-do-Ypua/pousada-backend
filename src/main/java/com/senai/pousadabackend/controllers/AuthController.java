package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.auth.LoginDTO;
import com.senai.pousadabackend.domain.auth.TokenDTO;
import com.senai.pousadabackend.integration.KeycloakFeign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.client-id}")
    private String clientId;

    private final KeycloakFeign keycloakFeign;

    public AuthController(KeycloakFeign keycloakFeign) {
        this.keycloakFeign = keycloakFeign;
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody LoginDTO login) {
        Map<String, String> form = new HashMap<>();
        form.put("client_id", clientId);
        form.put("client_secret", clientSecret);
        form.put("username", login.getUsername());
        form.put("password", login.getSenha());
        form.put("grant_type", "password");

        TokenDTO token = keycloakFeign.login(form);
        return ResponseEntity.ok(token);
    }
}
