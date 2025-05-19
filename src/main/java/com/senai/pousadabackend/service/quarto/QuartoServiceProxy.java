package com.senai.pousadabackend.service.quarto;

import com.senai.pousadabackend.entity.Quarto;
import com.senai.pousadabackend.repository.QuartoRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuartoServiceProxy implements QuartoService {

    private final QuartoService delegate;

    public QuartoServiceProxy(QuartoService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Quarto salvar(Quarto quarto) {
        return delegate.salvar(quarto);
    }

    @Override
    public Quarto buscarPorId(Long id) {
        return delegate.buscarPorId(id);
    }

    @Override
    public Quarto excluir(Long id) {
        return delegate.excluir(id);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<Quarto> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Quarto> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

    @Override
    public Page<Quarto> listarInativos(Pageable pageable) {
        return delegate.listarInativos(pageable);
    }

}
