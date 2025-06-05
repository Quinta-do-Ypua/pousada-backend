package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplementoRepository extends BaseRepository<Complemento, Long> {

    @Query("SELECT c "
            + "FROM Complemento c "
            + "WHERE c.nome = :nome "
            + "AND c.status != 'INATIVO' ")
    Complemento buscarPor(String nome);
}
