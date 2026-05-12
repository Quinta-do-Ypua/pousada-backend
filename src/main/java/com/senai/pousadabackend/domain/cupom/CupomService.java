package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.core.base.BaseService;
import com.senai.pousadabackend.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CupomService extends BaseService<Cupom, Long, CupomRepository> {

    private final CupomRepository repository;

    public CupomService(CupomRepository repo) {
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
        validarCodigosIguaisDo(cupom);
    }

    private void validarCodigosIguaisDo(Cupom cupom) {
        Cupom cupomEncontrado = repository.findByCodigo(cupom.getCodigo());
        if (cupomEncontrado != null && !cupomEncontrado.getId().equals(cupom.getId())) {
            throw new BusinessException("Já existe um cupom salvo com o mesmo código");
        }
    }

    private void validarPeriodoDo(Cupom cupom) {
        if (cupom.getDataDeInicio().isAfter(cupom.getDataDeVencimento())) {
            throw new BusinessException("A data de início não deve ser posterior a data de vencimento");
        }

        if (cupom.isExistente()) {
            Cupom cupomEncontrado = this.buscarPorId(cupom.getId());
            if (cupomEncontrado.getDataDeInicio().isAfter(cupom.getDataDeInicio())) {
                throw new BusinessException("A data de início não pode ser anterior a data inicial cadastrada");
            }
        } else {
            LocalDate dataAtual = LocalDate.now();
            if (dataAtual.isAfter(cupom.getDataDeInicio())) {
                throw new BusinessException("A data inicial deve ser posterior a data atual");
            }
        }
    }

}
