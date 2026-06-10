package com.senai.pousadabackend.domain.temaSistema;

import com.senai.pousadabackend.core.base.BaseService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TemaSistemaService extends BaseService<TemaSistema, Long, TemaSistemaRepository> {

    private final TemaSistemaRepository repo;

    public TemaSistemaService(TemaSistemaRepository repo) {
        super(repo);
        this.repo = repo;
    }

    @Transactional
    public TemaSistema buscarOuCriarPadrao(Long id) {
        return repo.findById(id)
                .orElseGet(() -> repo.save(TemaSistema.builder().id(id).build()));
    }

    @Transactional
    public void excluirSeExistir(Long id) {
        repo.findById(id).ifPresent(repo::delete);
    }

}
