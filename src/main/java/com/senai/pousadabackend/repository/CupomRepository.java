package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.domain.cupom.Cupom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends BaseRepository<Cupom, Long> {

    @Query("SELECT c "
            + "FROM Cupom c "
            + "WHERE c.nome = :nome ")
    Cupom buscarPor(String nome);
}
