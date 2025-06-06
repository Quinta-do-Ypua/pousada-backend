package com.senai.pousadabackend.domain.resumo;

import com.senai.pousadabackend.domain.reserva.Reserva;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ResumoReservaServiceProxy implements ResumoReservaService {

    private final ResumoReservaService delegate;

    public ResumoReservaServiceProxy(@Qualifier("resumoReservaServiceImpl") ResumoReservaService delegate) {
        this.delegate = delegate;
    }

    @Override
    public ResumoReserva salvar(ResumoReserva resumoReserva) {
        return delegate.salvar(resumoReserva);
    }

    @Override
    public ResumoReserva buscarPorId(Long aLong) {
        return delegate.buscarPorId(aLong);
    }

    @Override
    public ResumoReserva excluir(Long aLong) {
        return delegate.excluir(aLong);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<ResumoReserva> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<ResumoReserva> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

    @Override
    public void criarNotaFiscalAssincronaAPartirDaReserva(Reserva reserva) {
        delegate.criarNotaFiscalAssincronaAPartirDaReserva(reserva);
    }

    @Override
    public ResumoReserva criarERetornarNotaFiscalAPartirDaReserva(Reserva reserva) {
        return delegate.criarERetornarNotaFiscalAPartirDaReserva(reserva);
    }

}
