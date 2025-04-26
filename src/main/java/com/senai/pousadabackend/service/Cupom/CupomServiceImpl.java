package com.senai.pousadabackend.service.Cupom;

import com.senai.pousadabackend.entity.Cupom;
import com.senai.pousadabackend.repository.CupomRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CupomServiceImpl extends BaseService<Cupom, Long, CupomRepository> implements CupomService {

    private CupomRepository repository;

    public CupomServiceImpl(CupomRepository repo) {
        super(repo);
        this.repository = repo;
    }

    @Override
    public Cupom salvar(Cupom cupom) {
        return null;
    }

    @Override
    public Cupom buscarPorId(Long aLong) {
        return null;
    }

    @Override
    public Cupom excluir(Long aLong) {
        return null;
    }

    @Override
    public Cupom alterar(Cupom cupom) {
        return null;
    }
}
