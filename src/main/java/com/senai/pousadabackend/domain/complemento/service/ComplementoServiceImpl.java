package com.senai.pousadabackend.domain.complemento.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.complemento.Complemento;
import com.senai.pousadabackend.domain.complemento.ComplementoRepository;
import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import com.senai.pousadabackend.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class ComplementoServiceImpl extends BaseService<Complemento, Long, ComplementoRepository> implements ComplementoService {

    private final ComplementoRepository repository;
    private final ReservaRepository reservaRepository;

    public ComplementoServiceImpl(
            ComplementoRepository repo,
            ReservaRepository reservaRepository) {
        super(repo);
        this.repository = repo;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Complemento salvar(Complemento complemento) {
        this.validarNomesIguaisDo(complemento);
        return super.salvar(complemento);
    }

    @Override
    public Complemento excluir(Long id) {
        if (reservaRepository.existsByComplementos_Id(id)) {
            throw new BusinessException("Este complemento está vinculado a uma reserva e não pode ser excluído.");
        }
        return super.excluir(id);
    }

    private void validarNomesIguaisDo(Complemento complemento) {
        Complemento complementoEncontrado = repository.findByNome(complemento.getNome());
        if (complementoEncontrado != null && !complementoEncontrado.getId().equals(complemento.getId())) {
            throw new BusinessException("Já existe um complemento salvo com o mesmo nome");
        }
    }

    private void validarVinculoComReserva() {

    }
}
