package com.senai.pousadabackend.domain.security;

public record ResetPasswordRequestDTO(String token, String novaSenha) {}
