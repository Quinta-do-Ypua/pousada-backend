package com.senai.pousadabackend.domain.quarto;

import com.senai.pousadabackend.core.repository.BaseRepository;

public interface QuartoRepository extends BaseRepository<Quarto, Long> {

    Quarto findByNome(String nome);
}
