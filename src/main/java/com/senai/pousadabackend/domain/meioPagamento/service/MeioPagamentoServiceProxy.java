package com.senai.pousadabackend.domain.meioPagamento.service;

import com.senai.pousadabackend.domain.meioPagamento.MeioPagamento;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MeioPagamentoServiceProxy implements MeioPagamentoService {

    private final MeioPagamentoService delegate;

    public MeioPagamentoServiceProxy(MeioPagamentoService delegate) {
        this.delegate = delegate;
    }

    @Override
    public MeioPagamento salvar(MeioPagamento meioPagamento) {
        return delegate.salvar(meioPagamento);
    }

    @Override
    public MeioPagamento buscarPorId(Long aLong) {
        return delegate.buscarPorId(aLong);
    }

    @Override
    public MeioPagamento excluir(Long aLong) {
        return delegate.excluir(aLong);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<MeioPagamento> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<MeioPagamento> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }
}
