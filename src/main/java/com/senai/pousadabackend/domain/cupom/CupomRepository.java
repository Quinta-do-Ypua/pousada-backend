package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.core.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CupomRepository extends BaseRepository<Cupom, Long> {

    Cupom findByCodigo(String codigo);

    @Query("""
        SELECT c FROM Cupom c
        WHERE :hoje >= c.dataDeInicio
        AND :hoje <= c.dataDeVencimento
        AND (SELECT COUNT(r) FROM Reserva r
             WHERE r.cupom = c
             AND r.statusDaReserva != 'CANCELADA') < c.quantidadeMaximaDeUso
    """)
    List<Cupom> findDisponiveis(@Param("hoje") LocalDate hoje);
}
