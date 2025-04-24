package com.senai.pousadabackend.service;

import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import org.springframework.data.jpa.repository.JpaRepository;

public class BaseServiceImpl<T, ID, R extends JpaRepository<T, ID>> implements BaseService<T, ID> {

    private final R repo;

    public BaseServiceImpl(R repo) {
        this.repo = repo;
    }
    @Override
    public T salvar(T t) {
        return repo.save(t);
    }

    @Override
    public T buscarPorId(ID id) {
        if (id == null) throw new IllegalArgumentException("O Id é obrigatório.");
        return repo.findById(id).orElseThrow(RegistroNaoEncontradoException::new);
    }

    @Override
    public T excluir(ID id) {
        T t = buscarPorId(id);
        repo.deleteById(id);
        return t;
    }
}