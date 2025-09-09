package com.senai.pousadabackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.senai.pousadabackend.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private  String secret;

    public String generateResetPasswordToken(Usuario user, int minutes) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("pousada-auth")
                    .withSubject(user.getEmail())
                    .withClaim("purpose", "RESET_PASSWORD")
                    .withExpiresAt(generateExpirationDate(minutes))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro durante a geração do token.");
        }
    }

    public String generateToken(Usuario user, int minutes) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("pousada-auth")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate(minutes))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro durante a autenticação.");
        }
    }

    public String validateResetPasswordToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var decoded = JWT.require(algorithm)
                    .withIssuer("pousada-auth")
                    .build()
                    .verify(token);
            String purpose = decoded.getClaim("purpose").asString();
            if (!"RESET_PASSWORD".equals(purpose)) {
                throw new IllegalArgumentException("Token inválido para esta operação");
            }
            return decoded.getSubject();
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Token inválido ou expirado");
        }
    }

    private Instant generateExpirationDate(int minutes) {
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("pousada-auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Token inválido ou expirado");
        }
    }

}