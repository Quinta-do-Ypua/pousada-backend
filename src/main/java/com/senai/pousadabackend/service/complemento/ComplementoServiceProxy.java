package com.senai.pousadabackend.service.complemento;

import com.senai.pousadabackend.entity.Complemento;
import com.senai.pousadabackend.repository.ComplementoRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public void isExists(Long id) {
        service.isExists(id);
    }

    @Override
    public Page<Complemento> buscarPorSpecification(String parametro, Pageable pageable) {
        return service.buscarPorSpecification(parametro, pageable);
    }
}
