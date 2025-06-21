package com.senai.pousadabackend.domain.usuario.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.usuario.Usuario;
import com.senai.pousadabackend.domain.usuario.UsuarioRepository;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl extends BaseService<Usuario, Long, UsuarioRepository> implements UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repo) {
        super(repo);
        this.repository = repo;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        validarEmailIguaisDo(usuario);
        return repository.save(usuario);
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
