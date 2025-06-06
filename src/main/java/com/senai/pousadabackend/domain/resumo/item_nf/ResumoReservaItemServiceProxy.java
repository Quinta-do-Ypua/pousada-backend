package com.senai.pousadabackend.domain.resumo.item_nf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ResumoReservaItemServiceProxy implements ResumoReservaItemService {

    private final ResumoReservaItemService delegate;

    public ResumoReservaItemServiceProxy(@Qualifier("resumoReservaItemServiceImpl") ResumoReservaItemService service) {
        this.delegate = service;
    }

    @Override
    public ResumoReservaItem salvar(ResumoReservaItem resumoReservaItem) {
        return delegate.salvar(resumoReservaItem);
    }

    @Override
    public ResumoReservaItem buscarPorId(Long aLong) {
        return delegate.buscarPorId(aLong);
    }

    @Override
    public ResumoReservaItem excluir(Long aLong) {
        return delegate.excluir(aLong);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<ResumoReservaItem> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<ResumoReservaItem> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

}
