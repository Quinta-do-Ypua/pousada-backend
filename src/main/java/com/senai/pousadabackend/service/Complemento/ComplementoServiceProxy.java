package com.senai.pousadabackend.service.Complemento;

import com.senai.pousadabackend.entity.Complemento;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ComplementoServiceProxy implements ComplementoService {

    @Qualifier("complementoServiceImpl")
    private final ComplementoService service;

    public ComplementoServiceProxy(ComplementoService service) {
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
}
