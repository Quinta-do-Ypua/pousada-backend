package com.senai.pousadabackend.domain.nota_fiscal.service;

import com.senai.pousadabackend.domain.nota_fiscal.NotaFiscal;
import com.senai.pousadabackend.domain.reserva.Reserva;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotaFiscalServiceProxy implements NotaFiscalService {

    private final NotaFiscalService delegate;

    public NotaFiscalServiceProxy(@Qualifier("notaFiscalServiceImpl") NotaFiscalService delegate) {
        this.delegate = delegate;
    }

    @Override
    public NotaFiscal salvar(NotaFiscal notaFiscal) {
        return delegate.salvar(notaFiscal);
    }

    @Override
    public NotaFiscal buscarPorId(Long aLong) {
        return delegate.buscarPorId(aLong);
    }

    @Override
    public NotaFiscal excluir(Long aLong) {
        return delegate.excluir(aLong);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<NotaFiscal> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<NotaFiscal> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

    @Override
    public Page<NotaFiscal> listarInativos(Pageable pageable) {
        return delegate.listarInativos(pageable);
    }

    @Override
    public void criarNotaFiscalAssincronaAPartirDaReserva(Reserva reserva) {
        delegate.criarNotaFiscalAssincronaAPartirDaReserva(reserva);
    }

    @Override
    public NotaFiscal criarERetornarNotaFiscalAPartirDaReserva(Reserva reserva) {
        return delegate.criarERetornarNotaFiscalAPartirDaReserva(reserva);
    }

}
