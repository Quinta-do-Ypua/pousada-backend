package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.core.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends BaseRepository<Cupom, Long> {

    Cupom findByCodigo(String codigo);
}
