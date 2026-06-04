package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.core.base.BaseService;
import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import com.senai.pousadabackend.exceptions.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ComplementoService extends BaseService<Complemento, Long, ComplementoRepository> {

    private final ComplementoRepository repository;
    private final ReservaRepository reservaRepository;

    public ComplementoService(ComplementoRepository repo, ReservaRepository reservaRepository) {
        super(repo);
        this.repository = repo;
        this.reservaRepository = reservaRepository;
    }

    @Override
    @Transactional
    public Complemento salvar(Complemento complemento) {
        this.validarNomesIguaisDo(complemento);
        return super.salvar(complemento);
    }

    @Override
    @Transactional
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

}
