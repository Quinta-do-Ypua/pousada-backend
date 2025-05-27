package com.senai.pousadabackend.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseServiceInterface<T, ID> {

    T salvar(T t);

    T buscarPorId(ID id);

    T excluir(ID id);

    void throwIfNotExists(ID id);

    Page<T> buscarPorSpecification(String parametro, Pageable pageable);

    Page<T> listarPaginado(Pageable pageable);

    Page<T> listarInativos(Pageable pageable);

}