package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.core.repository.BaseRepository;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.quarto.Quarto;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends BaseRepository<Reserva, Long> {

    @Query("""
        SELECT r FROM Reserva r
        WHERE r.quarto = :quarto
        AND r.checkIn >= :checkIn
        AND r.checkOut <= :checkOut
    """)
    List<Reserva> findQuartosEntreCheckInECheckOut(LocalDateTime checkIn, LocalDateTime checkOut, Quarto quarto);

    List<Reserva> findReservaByCliente(Cliente cliente);
    
}
