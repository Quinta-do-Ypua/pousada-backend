package com.senai.pousadabackend.core.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BaseServiceInterface<T, ID> {

    T salvar(T t);

    T atualizar(T t);

    T buscarPorId(ID id);

    T excluir(ID id);

    void throwIfNotExists(ID id);

    Page<T> buscarPorSpecification(String parametro, Pageable pageable);

    Page<T> buscarComFiltros(Map<String, String> textFiltros, String rsqlSearch, Pageable pageable);

    Page<T> listarPaginado(Pageable pageable);

    List<T> salvarEmLote(List<T> ts);

}