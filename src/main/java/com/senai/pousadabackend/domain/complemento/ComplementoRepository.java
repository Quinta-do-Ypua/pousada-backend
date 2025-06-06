package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplementoRepository extends BaseRepository<Complemento, Long> {

    Complemento findByNome(String nome);
}
