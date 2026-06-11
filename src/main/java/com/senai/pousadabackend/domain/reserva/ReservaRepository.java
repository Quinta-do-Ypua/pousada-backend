package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.core.base.BaseRepository;
import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.cupom.Cupom;
import com.senai.pousadabackend.domain.quarto.Quarto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends BaseRepository<Reserva, Long> {

    @Query("""
        SELECT r FROM Reserva r
        WHERE r.quarto = :quarto
        AND r.statusDaReserva != 'CANCELADA'
        AND r.checkIn < :checkOut
        AND r.checkOut > :checkIn
        AND (:reservaId IS NULL OR r.id != :reservaId)
    """)
    List<Reserva> findConflitosDeQuarto(LocalDateTime checkIn, LocalDateTime checkOut, Quarto quarto, Long reservaId);

    List<Reserva> findByQuarto(Quarto quarto);

    boolean existsByComplementos_Id(Long complementoId);

    @Query("""
        SELECT r FROM Reserva r
        WHERE r.statusDaReserva != 'CANCELADA'
    """)
    Page<Reserva> buscarReservasAtivas(Pageable pageable);

    @Query("""
        SELECT COUNT(r) FROM Reserva r
        WHERE r.cliente = :cliente
        AND r.statusDaReserva = 'ABERTA'
    """)
    Long countReservasAtivasPorCliente(Cliente cliente);

    @Query("""
        SELECT MAX(r.checkOut) FROM Reserva r
        WHERE r.cliente = :cliente
        AND r.statusDaReserva != 'CANCELADA'
    """)
    LocalDateTime findUltimoCheckOutPorCliente(Cliente cliente);

    long countByCupomAndStatusDaReservaNot(Cupom cupom, StatusDaReserva status);

    @Query("SELECT r.statusDaReserva, COUNT(r) FROM Reserva r GROUP BY r.statusDaReserva")
    List<Object[]> countPorStatus();

    @Query("SELECT SUM(r.valorDaReserva) FROM Reserva r WHERE r.statusDaReserva != 'CANCELADA'")
    BigDecimal somarFaturamentoTotal();

    @Query("SELECT COUNT(r) FROM Reserva r WHERE YEAR(r.checkIn) = :ano AND MONTH(r.checkIn) = :mes")
    Long countPorMes(@Param("ano") int ano, @Param("mes") int mes);

    @Query("SELECT SUM(r.valorDaReserva) FROM Reserva r WHERE r.statusDaReserva != 'CANCELADA' AND YEAR(r.checkIn) = :ano AND MONTH(r.checkIn) = :mes")
    BigDecimal somarFaturamentoPorMes(@Param("ano") int ano, @Param("mes") int mes);

    @Query("""
        SELECT r FROM Reserva r
        WHERE r.statusDaReserva = 'ABERTA'
        AND r.checkOut <= :agora
    """)
    List<Reserva> findReservasParaConcluir(@Param("agora") LocalDateTime agora);

    @Query("""
        SELECT r FROM Reserva r
        WHERE r.checkIn >= :de AND r.checkIn <= :ate
        AND (:quartoId IS NULL OR r.quarto.id = :quartoId)
        AND (:clienteId IS NULL OR r.cliente.id = :clienteId)
        AND (:status IS NULL OR r.statusDaReserva = :status)
        ORDER BY r.checkIn ASC
    """)
    List<Reserva> findRelatorio(
        @Param("de") LocalDateTime de,
        @Param("ate") LocalDateTime ate,
        @Param("quartoId") Long quartoId,
        @Param("clienteId") Long clienteId,
        @Param("status") StatusDaReserva status
    );

    @Query("""
        SELECT r.quarto.nome, SUM(r.valorDaReserva)
        FROM Reserva r
        WHERE r.checkIn >= :de AND r.checkIn <= :ate
        AND r.statusDaReserva != 'CANCELADA'
        AND (:quartoId IS NULL OR r.quarto.id = :quartoId)
        AND (:clienteId IS NULL OR r.cliente.id = :clienteId)
        GROUP BY r.quarto.nome
        ORDER BY SUM(r.valorDaReserva) DESC
    """)
    List<Object[]> findFaturamentoPorQuartoNoPeriodo(
        @Param("de") LocalDateTime de,
        @Param("ate") LocalDateTime ate,
        @Param("quartoId") Long quartoId,
        @Param("clienteId") Long clienteId
    );

}
