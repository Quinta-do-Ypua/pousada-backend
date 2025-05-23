package com.senai.pousadabackend.service.usuario;

import com.senai.pousadabackend.entity.Usuario;
import com.senai.pousadabackend.entity.enums.Status;
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
        this.validar(usuario);
        return repository.save(usuario);
    }

    @Override
    public Usuario alterar(Usuario usuario) {
        this.validar(usuario);
        return null;
    }

    public void validar(Usuario usuario) {
        if (usuario.isExistente()) {
            if (Status.I.equals(usuario.getStatus())) {
                throw new BusinessException("O novo usuário não pode conter o status inativo");
            }
        } else {
            super.isExists(usuario.getId());
        }

        validarNomesIguaisDo(usuario);
    }

    private void validarNomesIguaisDo(Usuario usuario) {
        Usuario usuarioEncontrado = repository.buscarPor(usuario.getNome());
        boolean isNomeExistente = false;

        if (usuario.isExistente()) {
            isNomeExistente = true;
        } else {
            if (!usuario.getId().equals(usuarioEncontrado.getId())) {
                isNomeExistente = true;
            }
        }

        if (isNomeExistente) {
            throw new BusinessException("Já existe um usuário salvo com o mesmo nome");
        }
    }
}
