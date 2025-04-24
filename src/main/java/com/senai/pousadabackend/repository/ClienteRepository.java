package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
