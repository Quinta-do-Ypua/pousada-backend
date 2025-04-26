package com.senai.pousadabackend.service.Usuario;

import com.senai.pousadabackend.entity.Usuario;
import com.senai.pousadabackend.repository.UsuarioRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends BaseService<Usuario, Long, UsuarioRepository> implements UsuarioService {

    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repo) {
        super(repo);
        this.repository = repo;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Long aLong) {
        return null;
    }

    @Override
    public Usuario excluir(Long aLong) {
        return null;
    }

    @Override
    public Usuario alterar(Usuario usuario) {
        return null;
    }
}
