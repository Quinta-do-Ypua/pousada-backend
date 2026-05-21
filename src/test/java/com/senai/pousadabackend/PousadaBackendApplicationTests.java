package com.senai.pousadabackend;

import com.senai.pousadabackend.infraestructure.KeycloakAdminClient;
import com.senai.pousadabackend.infraestructure.KeycloakFeign;
import com.senai.pousadabackend.infraestructure.KeycloakTokenClient;
import com.senai.pousadabackend.infraestructure.imagem.MinioDeleteClient;
import com.senai.pousadabackend.infraestructure.imagem.MinioUploadClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class PousadaBackendApplicationTests {

    @MockitoBean
    JwtDecoder jwtDecoder;

    @MockitoBean
    KeycloakFeign keycloakFeign;

    @MockitoBean
    KeycloakAdminClient keycloakAdminClient;

    @MockitoBean
    KeycloakTokenClient keycloakTokenClient;

    @MockitoBean
    MinioUploadClient minioUploadClient;

    @MockitoBean
    MinioDeleteClient minioDeleteClient;

    @Test
    void contextLoads() {
    }

}
