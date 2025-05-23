package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.domain.quarto.Quarto;

public interface QuartoRepository extends BaseRepository<Quarto, Long> {

    Quarto findByNome(String nome);

}
