package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
