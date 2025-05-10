package com.senai.pousadabackend.service.complemento;

import com.senai.pousadabackend.entity.Complemento;
import com.senai.pousadabackend.entity.Cupom;
import com.senai.pousadabackend.entity.enums.Status;
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
        this.validar(complemento);
        return super.salvar(complemento);
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
        this.validar(complemento);
        return super.alterar(complemento);
    }

    public void validar(Complemento complemento) {
        if (complemento.isExistente()) {
            if (Status.I.equals(complemento.getStatus())) {
                throw new BusinessException("O novo complemento não pode conter o status inativo");
            }
        } else {
            super.isExists(complemento.getId());
        }

        validarNomesIguaisDo(complemento);
    }

    private void validarNomesIguaisDo(Complemento complemento) {
        Complemento complementoEncontrado = repository.buscarPor(complemento.getNome());
        boolean isNomeExistente = false;

        if (complementoEncontrado.isExistente()) {
            isNomeExistente = true;
        } else {
            if (!complemento.getId().equals(complementoEncontrado.getId())) {
                isNomeExistente = true;
            }
        }

        if (isNomeExistente) {
            throw new BusinessException("Já existe um complemento salvo com o mesmo nome");
        }
    }
}
