package com.senai.pousadabackend.domain.complemento.service;

import com.senai.pousadabackend.domain.complemento.Complemento;
import com.senai.pousadabackend.entity.Status;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.repository.ComplementoRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ComplementoServiceImpl extends BaseService<Complemento, Long, ComplementoRepository> implements ComplementoService {

    private final ComplementoRepository repository;

    public ComplementoServiceImpl(ComplementoRepository repo) {
        super(repo);
        this.repository = repo;
    }

    @Override
    public Complemento salvar(Complemento complemento) {
        this.validarNomesIguaisDo(complemento);
        return super.salvar(complemento);
    }

    private void validarNomesIguaisDo(Complemento complemento) {
        Complemento complementoEncontrado = repository.buscarPor(complemento.getNome());
        boolean isNomeExistente = false;

        if (complementoEncontrado != null) {
            if (!complemento.isExistente()) {
                isNomeExistente = true;
            } else {
                if (!complementoEncontrado.getId().equals(complemento.getId())) {
                    isNomeExistente = true;
                }
            }
        }

        if (isNomeExistente) {
            throw new BusinessException("Já existe um complemento salvo com o mesmo nome");
        }
    }
}
