package com.senai.pousadabackend.service.cupom;

import com.senai.pousadabackend.entity.Cupom;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CupomServiceProxy implements CupomService {

    @Qualifier("cupomServiceImpl")
    private final CupomService service;

    public CupomServiceProxy(CupomService service) {
        this.service = service;
    }

    @Override
    public Cupom salvar(Cupom cupom) {
        return service.salvar(cupom);
    }

    @Override
    public Cupom buscarPorId(Long id) {
        return service.buscarPorId(id);
    }

    @Override
    public Cupom excluir(Long id) {
        return service.excluir(id);
    }

    @Override
    public Cupom alterar(Cupom cupom) {
        return service.alterar(cupom);
    }

    @Override
    public void isExists(Long id) {
        service.isExists(id);
    }

    @Override
    public Page<Cupom> buscarPorSpecification(String parametro, Pageable pageable) {
        return service.buscarPorSpecification(parametro, pageable);
    }
}
