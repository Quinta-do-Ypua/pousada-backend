package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.core.base.BaseService;
import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import com.senai.pousadabackend.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CupomService extends BaseService<Cupom, Long, CupomRepository> {

    private final CupomRepository repository;
    private final ReservaRepository reservaRepository;

    public CupomService(CupomRepository repo, ReservaRepository reservaRepository) {
        super(repo);
        this.repository = repo;
        this.reservaRepository = reservaRepository;
    }

    public Cupom buscarCupomValido(String codigo) {
        Cupom cupom = repository.findByCodigo(codigo);
        if (cupom == null) {
            throw new BusinessException("Cupom não encontrado.");
        }
        LocalDate hoje = LocalDate.now();
        if (hoje.isBefore(cupom.getDataDeInicio()) || hoje.isAfter(cupom.getDataDeVencimento())) {
            throw new BusinessException("Cupom fora do período de validade.");
        }
        long usos = reservaRepository.countByCupomAndStatusDaReservaNot(cupom, StatusDaReserva.CANCELADA);
        if (usos >= cupom.getQuantidadeMaximaDeUso()) {
            throw new BusinessException("Cupom esgotado.");
        }
        return cupom;
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
