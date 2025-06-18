package com.senai.pousadabackend.domain.usuario.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.cupom.Cupom;
import com.senai.pousadabackend.domain.usuario.Usuario;
import com.senai.pousadabackend.domain.usuario.UsuarioRepository;
import com.senai.pousadabackend.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends BaseService<Usuario, Long, UsuarioRepository> implements UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repo) {
        super(repo);
        this.repository = repo;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        this.validarNomesIguaisDo(usuario);
        return repository.save(usuario);
    }

    private void validarNomesIguaisDo(Usuario usuario) {
        Usuario usuarioEncontrado = repository.findByNome(usuario.getNome());
        if (usuarioEncontrado != null && !usuarioEncontrado.getId().equals(usuario.getId())) {
            throw new BusinessException("Já existe um usuário salvo com o mesmo nome");
        }
    }
}
