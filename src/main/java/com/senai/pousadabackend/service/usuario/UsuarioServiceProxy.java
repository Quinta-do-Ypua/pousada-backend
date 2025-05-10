package com.senai.pousadabackend.service.usuario;

import com.senai.pousadabackend.entity.Usuario;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceProxy implements UsuarioService {

    @Qualifier("usuarioServiceImpl")
    private final UsuarioService service;

    public UsuarioServiceProxy(UsuarioService service) {
        this.service = service;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return service.salvar(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return service.buscarPorId(id);
    }

    @Override
    public Usuario excluir(Long id) {
        return service.excluir(id);
    }

    @Override
    public Usuario alterar(Usuario usuario) {
        return service.alterar(usuario);
    }

    @Override
    public void isExists(Long id) {
        service.isExists(id);
    }

    @Override
    public Page<Usuario> buscarPorSpecification(String parametro, Pageable pageable) {
        return service.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Usuario> listarPaginado(Pageable pageable) {
        return service.listarPaginado(pageable);
    }
}
