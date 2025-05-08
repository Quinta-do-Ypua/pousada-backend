package com.senai.pousadabackend.service;

import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import com.senai.pousadabackend.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;

@Slf4j
public class BaseService<T, ID, R extends BaseRepository<T, ID>> implements BaseServiceInterface<T, ID> {

    private final R repo;

    public BaseService(R repo) {
        this.repo = repo;
    }

    @Override
    public T salvar(T t) {
        return repo.save(t);
    }

    @Override
    public T buscarPorId(ID id) {
        if (id == null) throw new IllegalArgumentException("O Id é obrigatório.");
        return repo.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("O registro com o id '" + id + "' não foi encontrado"));
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

    @Override
    public Page<T> buscarPorSpecification(String parametro, Pageable pageable) {
        if (parametro == null || parametro.isBlank()) {
            return repo.findAll(pageable);
        }
        Specification<T> spec = toSpecification(parametro);
        return repo.findAll(spec, pageable);
    }

}