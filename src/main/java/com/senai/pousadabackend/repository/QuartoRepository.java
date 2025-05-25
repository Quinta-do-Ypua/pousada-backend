package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.domain.quarto.Quarto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuartoRepository extends BaseRepository<Quarto, Long> {

    Quarto findByNome(String nome);

    @Query("SELECT q FROM Quarto q LEFT JOIN FETCH q.urlImagens WHERE q.id = :id")
    Optional<Quarto> buscarPorIdComImagens(@Param("id") Long id);

}
