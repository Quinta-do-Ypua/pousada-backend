package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.entity.Complemento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplementoRepository extends JpaRepository<Complemento, Long> {
}
