package com.senai.pousadabackend.domain.amenidade;

import com.senai.pousadabackend.core.base.BaseService;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AmenidadeService extends BaseService<Amenidade, Long, AmenidadeRepository> {

    private final AmenidadeRepository repository;

    public AmenidadeService(AmenidadeRepository repo) {
        super(repo);
        this.repository = repo;
    }

    @Override
    @Transactional
    public Amenidade salvar(Amenidade amenidade) {
        Amenidade existente = repository.findByNome(amenidade.getNome());
        if (existente != null && !existente.getId().equals(amenidade.getId())) {
            throw new RegistroDuplicadoException("Já existe uma amenidade com o nome '" + amenidade.getNome() + "'.");
        }
        return super.salvar(amenidade);
    }

}
