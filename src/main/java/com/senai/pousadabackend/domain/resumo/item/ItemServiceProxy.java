package com.senai.pousadabackend.domain.resumo.item;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceProxy implements ItemService {

    private final ItemService delegate;

    public ItemServiceProxy(@Qualifier("itemServiceImpl") ItemService service) {
        this.delegate = service;
    }

    @Override
    public Item salvar(Item item) {
        return delegate.salvar(item);
    }

    @Override
    public Item buscarPorId(Long aLong) {
        return delegate.buscarPorId(aLong);
    }

    @Override
    public Item excluir(Long aLong) {
        return delegate.excluir(aLong);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<Item> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Item> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

    @Override
    public Page<Item> listarInativos(Pageable pageable) {
        return delegate.listarInativos(pageable);
    }

}
