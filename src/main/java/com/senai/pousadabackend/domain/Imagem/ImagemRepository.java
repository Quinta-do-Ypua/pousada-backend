package com.senai.pousadabackend.domain.Imagem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<ImagemQuarto, Long> {

    @Query("SELECT i "
            + "FROM ImagemQuarto i "
            + "WHERE i.quarto.id = :idQuarto ")
    List<ImagemQuarto> listarPor(Long idQuarto);
}
