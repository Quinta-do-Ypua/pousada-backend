package com.senai.pousadabackend.domain.amenidade.service;

import com.senai.pousadabackend.domain.amenidade.Amenidade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AmenidadeServiceProxy implements AmenidadeService {
    
    private final AmenidadeService delegate;
    
    public AmenidadeServiceProxy(@Qualifier("amenidadeServiceImpl") AmenidadeService delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public Amenidade salvar(Amenidade amenidade) {
        return delegate.salvar(amenidade);
    }

    @Override
    public Amenidade buscarPorId(Long id) {
        return delegate.buscarPorId(id);
    }

    @Override
    public Amenidade excluir(Long id) {
        return delegate.excluir(id);
    }

    @Override
    public void throwIfNotExists(Long id) {  
        delegate.throwIfNotExists(id);
    }

    @Override
    public Page<Amenidade> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Amenidade> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

}
