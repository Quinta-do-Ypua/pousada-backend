package com.senai.pousadabackend.service.complemento;

import com.senai.pousadabackend.entity.Complemento;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComplementoServiceProxy implements ComplementoService {

    private final ComplementoService service;

    public ComplementoServiceProxy(@Qualifier("complementoServiceImpl") ComplementoService service) {
        this.service = service;
    }

    @Override
    public Complemento salvar(Complemento complemento) {
        return null;
    }

    @Override
    public Complemento buscarPorId(Long aLong) {
        return null;
    }

    @Override
    public Complemento excluir(Long aLong) {
        return null;
    }

    @Override
    public Complemento alterar(Complemento complemento) {
        return null;
    }

    @Override
    public void isExists(Long id) {
        service.isExists(id);
    }

    @Override
    public Page<Complemento> buscarPorSpecification(String parametro, Pageable pageable) {
        return service.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Complemento> listarPaginado(Pageable pageable) {
        return service.listarPaginado(pageable);
    }

}
