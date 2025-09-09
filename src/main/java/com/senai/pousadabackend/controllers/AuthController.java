package com.senai.pousadabackend.controllers;


import com.senai.pousadabackend.domain.security.*;
import com.senai.pousadabackend.domain.usuario.Usuario;
import com.senai.pousadabackend.domain.usuario.UsuarioRepository;
import com.senai.pousadabackend.integration.sendgrid.EmailService;
import com.senai.pousadabackend.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body){
        Optional<Usuario> optionalUsuario = this.repository.findByEmail(body.email());
        if (optionalUsuario.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        Usuario usuario = optionalUsuario.get();
        if (passwordEncoder.matches(body.senha(), usuario.getSenha())) {
            String token = this.tokenService.generateToken(usuario, 120);
            return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token));
        }
        throw new IllegalArgumentException("Email ou senha inválidos");
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequestDTO body){
        Optional<Usuario> usuario = this.repository.findByEmail(body.email());

        if(usuario.isEmpty()) {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setSenha(passwordEncoder.encode(body.senha()));
            novoUsuario.setEmail(body.email());
            novoUsuario.setNome(body.nome());
            this.repository.save(novoUsuario);

            String token = this.tokenService.generateToken(novoUsuario, 120);
            return ResponseEntity.ok(new ResponseDTO(novoUsuario.getNome(), token));
        } else {
            throw new IllegalArgumentException("O e-mail informado já está sendo utilizado");
        }
    }

    @PutMapping("/register")
    public ResponseEntity alterar(@RequestBody RegisterRequestDTO body) {
        Optional<Usuario> usuarioOptional = this.repository.findByEmail(body.email());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setNome(body.nome());
            usuario.setSenha(passwordEncoder.encode(body.senha()));
            this.repository.save(usuario);

            String token = this.tokenService.generateToken(usuario, 120);
            return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token));
        }
        throw new IllegalArgumentException("Usuário com esse e-mail não encontrado.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> recuperarAcesso(@RequestBody ForgotPasswordRequestDTO body){
        Optional<Usuario> optionalUsuario = this.repository.findByEmail(body.email());
        if (optionalUsuario.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        Usuario usuario = optionalUsuario.get();
        String token = tokenService.generateResetPasswordToken(usuario, 15);
        emailService.sendResetPasswordEmail(usuario.getEmail(), token);
        return ResponseEntity.ok("E-mail de recuperação enviado para " + usuario.getEmail());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetarPassword(@RequestBody ResetPasswordRequestDTO body) {
        String email = tokenService.validateResetPasswordToken(body.token());
        Optional<Usuario> optionalUsuario = this.repository.findByEmail(email);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Token inválido ou usuário não encontrado");
        }
        Usuario usuario = optionalUsuario.get();
        usuario.setSenha(passwordEncoder.encode(body.novaSenha()));
        repository.save(usuario);
        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }

}