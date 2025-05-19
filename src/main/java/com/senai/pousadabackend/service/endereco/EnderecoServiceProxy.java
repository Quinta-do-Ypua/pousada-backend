package com.senai.pousadabackend.service.endereco;

import com.senai.pousadabackend.entity.Endereco;
import com.senai.pousadabackend.repository.EnderecoRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceProxy implements EnderecoService {

    private final EnderecoService delegate;

    public EnderecoServiceProxy(EnderecoService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Endereco salvar(Endereco endereco) {
        return delegate.salvar(endereco);
    }

    @Override
    public Endereco buscarPorId(Long aLong) {
        return delegate.buscarPorId(aLong);
    }

    @Override
    public Endereco excluir(Long aLong) {
        return delegate.excluir(aLong);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<Endereco> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Endereco> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

    @Override
    public Page<Endereco> listarInativos(Pageable pageable) {
        return delegate.listarInativos(pageable);
    }
}
