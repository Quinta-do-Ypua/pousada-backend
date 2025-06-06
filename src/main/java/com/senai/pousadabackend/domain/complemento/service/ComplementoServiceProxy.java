package com.senai.pousadabackend.domain.complemento.service;

import com.senai.pousadabackend.domain.complemento.Complemento;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComplementoServiceProxy implements ComplementoService {

    private final ComplementoService delegate;

    public ComplementoServiceProxy(@Qualifier("complementoServiceImpl") ComplementoService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Complemento salvar(Complemento complemento) {
        return delegate.salvar(complemento);
    }

    @Override
    public Complemento buscarPorId(Long aLong) {
        return delegate.buscarPorId(aLong);
    }

    @Override
    public Complemento excluir(Long aLong) {
        return delegate.excluir(aLong);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<Complemento> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Complemento> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

}
