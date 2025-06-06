package com.senai.pousadabackend.domain.cupom.service;


import com.senai.pousadabackend.domain.cupom.Cupom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CupomServiceProxy implements CupomService {

    private final CupomService delegate;

    public CupomServiceProxy(CupomService delegate) {
        this.delegate = delegate;
    }


    @Override
    public Cupom salvar(Cupom cupom) {
        return delegate.salvar(cupom);
    }

    @Override
    public Cupom buscarPorId(Long aLong) {
        return delegate.buscarPorId(aLong);
    }

    @Override
    public Cupom excluir(Long aLong) {
        return delegate.excluir(aLong);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<Cupom> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Cupom> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

}
