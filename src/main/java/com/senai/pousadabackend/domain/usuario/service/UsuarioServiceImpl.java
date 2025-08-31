package com.senai.pousadabackend.domain.usuario.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.auth.KeycloakClientResponseDTO;
import com.senai.pousadabackend.domain.auth.KeycloakRoleResponseDTO;
import com.senai.pousadabackend.domain.auth.KeycloakTokenResponseDTO;
import com.senai.pousadabackend.domain.auth.KeycloakUserRequestDTO;
import com.senai.pousadabackend.domain.usuario.Usuario;
import com.senai.pousadabackend.domain.usuario.UsuarioRepository;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import com.senai.pousadabackend.integration.KeycloakAdminClient;
import com.senai.pousadabackend.integration.KeycloakTokenClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioServiceImpl extends BaseService<Usuario, Long, UsuarioRepository> implements UsuarioService {

    private final UsuarioRepository repository;
    private final KeycloakTokenClient tokenClient;
    private final KeycloakAdminClient adminClient;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public UsuarioServiceImpl(UsuarioRepository repo,
                              KeycloakTokenClient tokenClient,
                              KeycloakAdminClient adminClient) {
        super(repo);
        this.repository = repo;
        this.tokenClient = tokenClient;
        this.adminClient = adminClient;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        validarEmailIguaisDo(usuario);

        String token = "Bearer " + getClientToken();

        KeycloakUserRequestDTO userRequest = new KeycloakUserRequestDTO(
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getSenha()
        );

        ResponseEntity<Void> response = adminClient.createUser(token, userRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            String location = Objects.requireNonNull(response.getHeaders().getLocation()).toString();
            String userId = location.substring(location.lastIndexOf("/") + 1);

            assignClientRoles(userId, clientId, usuario.getRoles(), token);
        }

        return repository.save(usuario);
    }

    private String getClientToken() {
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("client_id", clientId);
        formParams.add("client_secret", clientSecret);
        formParams.add("grant_type", "client_credentials");

        KeycloakTokenResponseDTO tokenResponse = tokenClient.getToken(formParams);
        return tokenResponse.getAccessToken();
    }

    private void assignClientRoles(String userId, String clientId, List<String> roles, String token) {
        List<KeycloakClientResponseDTO> clients = adminClient.getClients(token, clientId);

        if (clients.isEmpty()) {
            throw new RuntimeException("Client não encontrado no Keycloak: " + clientId);
        }

        String clientUuid = clients.getFirst().getId();
        List<KeycloakRoleResponseDTO> allRoles = adminClient.getClientRoles(token, clientUuid);

        List<Map<String, String>> selectedRoles = allRoles.stream()
                .filter(r -> roles.contains(r.getName()))
                .map(r -> Map.of("id", r.getId(), "name", r.getName()))
                .toList();

        adminClient.assignClientRoles(token, userId, clientUuid, selectedRoles);
    }

    private void validarEmailIguaisDo(Usuario usuario) {
        Optional<Usuario> existente = repository.findByEmail(usuario.getEmail());

        if (existente.isPresent()) {
            Usuario encontrado = existente.get();
            boolean mesmoUsuario = usuario.getId() != null && usuario.getId().equals(encontrado.getId());

            if (!mesmoUsuario) {
                throw new BusinessException("Já existe um usuário com este e-mail.");
            }
        }
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        var usuario = repository.findByEmail(email);
        if (usuario.isEmpty())
            throw new RegistroNaoEncontradoException("Usuário não encontrado para o email: " + email);
        return usuario.get();
    }
}
