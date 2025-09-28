package com.senai.pousadabackend.domain.usuario.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.auth.KeycloakClientResponseDTO;
import com.senai.pousadabackend.domain.auth.KeycloakRoleResponseDTO;
import com.senai.pousadabackend.domain.auth.KeycloakTokenResponseDTO;
import com.senai.pousadabackend.domain.auth.KeycloakUserRequestDTO;
import com.senai.pousadabackend.domain.usuario.Usuario;
import com.senai.pousadabackend.domain.usuario.UsuarioRepository;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.exceptions.KeycloakConfigurationException;
import com.senai.pousadabackend.exceptions.KeycloakIntegrationException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import com.senai.pousadabackend.integration.KeycloakAdminClient;
import com.senai.pousadabackend.integration.KeycloakTokenClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioServiceImpl extends BaseService<Usuario, Long, UsuarioRepository> implements UsuarioService {

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final UsuarioRepository repository;
    private final KeycloakTokenClient tokenClient;
    private final KeycloakAdminClient adminClient;

    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    private static final String BEARER_PREFIX = "Bearer ";

    public UsuarioServiceImpl(UsuarioRepository repo,
                              KeycloakTokenClient tokenClient,
                              KeycloakAdminClient adminClient) {
        super(repo);
        this.repository = repo;
        this.tokenClient = tokenClient;
        this.adminClient = adminClient;
    }

    public Usuario salvar(Usuario usuario) {
        validarEmailIguaisDo(usuario);

        String token = getClientToken();
        String userIdKeycloak = null;

        try {
            userIdKeycloak = createUserInKeycloak(usuario, token);

            assignClientRoles(userIdKeycloak, usuario.getRoles(), token);

            usuario.setKeycloakId(userIdKeycloak);
            usuario.setSenha(null);

            return repository.save(usuario);

        } catch (Exception e) {
//            if (userIdKeycloak != null) {
//                deleteUserInKeycloak(userIdKeycloak, token);
//            }
            throw new KeycloakIntegrationException("Falha ao criar e configurar usuário no Keycloak.");
        }
    }

    private String createUserInKeycloak(Usuario usuario, String token) {
        KeycloakUserRequestDTO userRequest = new KeycloakUserRequestDTO(
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getSenha()
        );

        ResponseEntity<Void> response = adminClient.createUser(BEARER_PREFIX + token, userRequest);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new KeycloakIntegrationException("Keycloak retornou status " + response.getStatusCode() + " ao criar usuário.");
        }

        return extractUserIdFromLocationHeader(response);
    }

    private void assignClientRoles(String userId, List<String> roles, String token) {
        String clientUuid = findClientUuid(token);

        List<KeycloakRoleResponseDTO> allAvailableRoles = adminClient.getClientRoles(BEARER_PREFIX + token, clientUuid);

        List<Map<String, String>> rolesToAssign = allAvailableRoles.stream()
                .filter(availableRole -> roles.contains(availableRole.getName()))
                .map(role -> Map.of("id", role.getId(), "name", role.getName()))
                .toList();

        if (rolesToAssign.size() != roles.size()) {
            throw new KeycloakConfigurationException("Uma ou mais roles especificadas não foram encontradas no Keycloak.");
        }

        adminClient.assignClientRoles(BEARER_PREFIX + token, userId, clientUuid, rolesToAssign);
    }

    private String getClientToken() {
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("client_id", clientId);
        formParams.add("client_secret", clientSecret);
        formParams.add("grant_type", GRANT_TYPE_CLIENT_CREDENTIALS);

        KeycloakTokenResponseDTO tokenResponse = tokenClient.getToken(formParams);
        return tokenResponse.getAccessToken();
    }

    private String findClientUuid(String token) {
        List<KeycloakClientResponseDTO> clients = adminClient.getClients(BEARER_PREFIX + token, clientId);
        return clients.stream()
                .findFirst()
                .map(KeycloakClientResponseDTO::getId)
                .orElseThrow(() -> new KeycloakConfigurationException("Client '" + clientId + "' não encontrado no Keycloak."));
    }

    private String extractUserIdFromLocationHeader(ResponseEntity<Void> response) {
        URI location = response.getHeaders().getLocation();
        if (location == null) {
            throw new KeycloakIntegrationException("Keycloak não retornou o header 'Location' após a criação do usuário.");
        }

        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

//    private void deleteUserInKeycloak(String userId, String token) {
//        try {
//            adminClient.deleteUser(BEARER_PREFIX + token, userId);
//        } catch (Exception ex) {
//            // Loga um erro crítico. A remoção manual pode ser necessária.
//            // logger.error("FALHA CRÍTICA: Não foi possível remover o usuário fantasma '{}' do Keycloak.", userId, ex);
//        }
//    }

//    @Override
//    public Usuario salvar(Usuario usuario) {
//        validarEmailIguaisDo(usuario);
//
//        String token = "Bearer " + getClientToken();
//
//        KeycloakUserRequestDTO userRequest = new KeycloakUserRequestDTO(
//                usuario.getEmail(),
//                usuario.getNome(),
//                usuario.getSenha()
//        );
//
//        ResponseEntity<Void> response = adminClient.createUser(token, userRequest);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            String location = Objects.requireNonNull(response.getHeaders().getLocation()).toString();
//            String userId = location.substring(location.lastIndexOf("/") + 1);
//
//            assignClientRoles(userId, clientId, usuario.getRoles(), token);
//        }
//
//        return repository.save(usuario);
//    }
//
//    private String getClientToken() {
//        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
//        formParams.add("client_id", clientId);
//        formParams.add("client_secret", clientSecret);
//        formParams.add("grant_type", "client_credentials");
//
//        KeycloakTokenResponseDTO tokenResponse = tokenClient.getToken(formParams);
//        return tokenResponse.getAccessToken();
//    }
//
//    private void assignClientRoles(String userId, String clientId, List<String> roles, String token) {
//        List<KeycloakClientResponseDTO> clients = adminClient.getClients(token, clientId);
//
//        if (clients.isEmpty()) {
//            throw new RuntimeException("Client não encontrado no Keycloak: " + clientId);
//        }
//
//        String clientUuid = clients.getFirst().getId();
//        List<KeycloakRoleResponseDTO> allRoles = adminClient.getClientRoles(token, clientUuid);
//
//        List<Map<String, String>> selectedRoles = allRoles.stream()
//                .filter(r -> roles.contains(r.getName()))
//                .map(r -> Map.of("id", r.getId(), "name", r.getName()))
//                .toList();
//
//        adminClient.assignClientRoles(token, userId, clientUuid, selectedRoles);
//    }

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
