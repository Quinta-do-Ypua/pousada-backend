package com.senai.pousadabackend.service;

public interface BaseService<T, ID> {

    T salvar(T t);

    T buscarPorId(ID id);

    T excluir(ID id);

    T alterar(T t);

}