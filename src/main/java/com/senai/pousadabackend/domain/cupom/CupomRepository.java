package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends BaseRepository<Cupom, Long> {

    @Query("SELECT c "
            + "FROM Cupom c "
            + "WHERE UPPER(c.codigo) = UPPER(:codigo) "
            + "AND c.status != 'INATIVO' ")
    Cupom buscarPor(String codigo);
}
