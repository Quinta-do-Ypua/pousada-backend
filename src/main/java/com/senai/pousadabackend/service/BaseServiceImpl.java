package com.senai.pousadabackend.service;

import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;

@Slf4j
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

    @Override
    public T alterar(T t) {
        try {
            Field field = t.getClass().getDeclaredField("id");
            field.setAccessible(true);
            ID valorId = (ID) field.get(t);
            buscarPorId(valorId);
            return this.salvar(t);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.error("Erro: {} \n StackTrace: {}", e.getMessage(), e.getStackTrace());
            throw new IllegalArgumentException("O campo id é obrigatório no corpo da requisição");
        }
    }
}