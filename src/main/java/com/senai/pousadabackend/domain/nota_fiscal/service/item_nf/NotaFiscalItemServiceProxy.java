package com.senai.pousadabackend.domain.nota_fiscal.service.item_nf;

import com.senai.pousadabackend.domain.nota_fiscal.item_nf.NotaFiscalItem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotaFiscalItemServiceProxy implements NotaFiscalItemService {

    private final NotaFiscalItemService delegate;

    public NotaFiscalItemServiceProxy(@Qualifier("notaFiscalItemServiceImpl") NotaFiscalItemService service) {
        this.delegate = service;
    }

    @Override
    public NotaFiscalItem salvar(NotaFiscalItem notaFiscalItem) {
        return delegate.salvar(notaFiscalItem);
    }

    @Override
    public NotaFiscalItem buscarPorId(Long aLong) {
        return delegate.buscarPorId(aLong);
    }

    @Override
    public NotaFiscalItem excluir(Long aLong) {
        return delegate.excluir(aLong);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<NotaFiscalItem> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<NotaFiscalItem> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

    @Override
    public Page<NotaFiscalItem> listarInativos(Pageable pageable) {
        return delegate.listarInativos(pageable);
    }

}
