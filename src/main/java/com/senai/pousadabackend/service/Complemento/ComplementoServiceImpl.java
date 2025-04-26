package com.senai.pousadabackend.service.Complemento;

import com.senai.pousadabackend.entity.Complemento;
import com.senai.pousadabackend.repository.ComplementoRepository;
import com.senai.pousadabackend.service.BaseService;

public class ComplementoServiceImpl extends BaseService<Complemento, Long, ComplementoRepository> implements ComplementoService {

    private ComplementoRepository repository;

    public ComplementoServiceImpl(ComplementoRepository repo) {
        super(repo);
        this.repository = repo;
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
