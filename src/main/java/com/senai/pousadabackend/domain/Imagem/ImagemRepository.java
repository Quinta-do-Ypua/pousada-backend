package com.senai.pousadabackend.domain.Imagem;

import com.senai.pousadabackend.domain.quarto.UrlImagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends JpaRepository<UrlImagem, Long> {
}
