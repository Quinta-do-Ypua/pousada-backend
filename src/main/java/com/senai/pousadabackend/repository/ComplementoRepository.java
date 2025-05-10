package com.senai.pousadabackend.repository;

import com.senai.pousadabackend.entity.Complemento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplementoRepository extends BaseRepository<Complemento, Long> {

    @Query("SELECT c "
            + "FROM Complemento c "
            + "WHERE c.nome = :nome ")
    Complemento buscarPor(String nome);
}
