package com.senai.pousadabackend.service.Cupom;

import com.senai.pousadabackend.entity.Cupom;
import org.springframework.beans.factory.annotation.Qualifier;
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
}
