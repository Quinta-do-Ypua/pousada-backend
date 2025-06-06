package com.senai.pousadabackend.domain.usuario.service;

import com.senai.pousadabackend.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceProxy implements UsuarioService {

    private final UsuarioService delegate;

    public UsuarioServiceProxy(UsuarioService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return delegate.salvar(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return delegate.buscarPorId(id);
    }

    @Override
    public Usuario excluir(Long id) {
        return delegate.excluir(id);
    }

    @Override
    public void throwIfNotExists(Long id) {
        delegate.throwIfNotExists(id);
    }

    @Override
    public Page<Usuario> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Usuario> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

}
