package com.senai.pousadabackend.service;


import com.senai.pousadabackend.exceptions.RegistroNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public abstract class BaseService<T, ID> {

    @Autowired
    private JpaRepository<T, ID> repository;

    public T salvar(T entity) {
        log.info("Salvando entidade {}", entity);
        return repository.save(entity);
    }

    public T atualizar(ID id, T entity) {
        buscarPorId(id);
        log.info("Atualizando entidade {}", entity);
        return salvar(entity);
    }

    public Page<T> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T buscarPorId(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado com ID " + id));
    }

}
