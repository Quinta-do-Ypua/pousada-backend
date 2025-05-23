package com.senai.pousadabackend.domain.usuario.service;

import com.senai.pousadabackend.domain.usuario.Usuario;
import com.senai.pousadabackend.entity.Status;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.repository.UsuarioRepository;
import com.senai.pousadabackend.service.BaseService;
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
        Usuario usuarioEncontrado = repository.buscarPor(usuario.getNome());
        boolean isNomeExistente = false;

        if (usuarioEncontrado != null) {
            if (!usuario.isExistente()) {
                isNomeExistente = true;
            } else {
                if (!usuarioEncontrado.getId().equals(usuario.getId())) {
                    isNomeExistente = true;
                }
            }
        }

        if (isNomeExistente) {
            throw new BusinessException("Já existe um usuário salvo com o mesmo nome");
        }
    }
}
