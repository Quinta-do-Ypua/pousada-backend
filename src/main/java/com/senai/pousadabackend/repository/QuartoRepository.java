package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.entity.Quarto;

public interface QuartoRepository extends BaseRepository<Quarto, Long> {

    boolean existsByNome(String numero);
}
