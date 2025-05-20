package com.senai.pousadabackend.service.cupom;

import com.senai.pousadabackend.entity.Cupom;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.repository.CupomRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CupomServiceImpl extends BaseService<Cupom, Long, CupomRepository> implements CupomService {

    private final CupomRepository repository;

    public CupomServiceImpl(CupomRepository repo) {
        super(repo);
        this.repository = repo;
    }

    @Override
    public Cupom salvar(Cupom cupom) {
        this.validar(cupom);
        return super.salvar(cupom);
    }

    private void validar(Cupom cupom) {
        validarPeriodoDo(cupom);
        validarNomesIguaisDo(cupom);
    }

    private void validarNomesIguaisDo(Cupom cupom) {
        Cupom cupomEncontrado = repository.buscarPor(cupom.getNome());
        boolean isNomeExistente = false;

        if (cupomEncontrado != null) {
            if (cupom.isExistente()) {
                isNomeExistente = true;
            } else {
                if (!cupomEncontrado.getId().equals(cupom.getId())) {
                    isNomeExistente = true;
                }
            }
        }

        if (isNomeExistente) {
            throw new BusinessException("Já existe um cupom salvo com o mesmo nome");
        }
    }

    private void validarPeriodoDo(Cupom cupom) {
        if (cupom.getDataDeInicio().isAfter(cupom.getDataDeVencimento())) {
            throw new BusinessException("A data de início não deve ser posterior a data de vencimento");
        }

        LocalDate dataAtual = LocalDate.now();

        if (dataAtual.isAfter(cupom.getDataDeInicio())) {
            throw new BusinessException("A data inicial deve ser posterior a data atual");
        }
    }
}
