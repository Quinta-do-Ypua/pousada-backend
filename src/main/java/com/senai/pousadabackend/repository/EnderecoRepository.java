package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
