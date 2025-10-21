package com.senai.pousadabackend.domain.configuracao;

import com.senai.pousadabackend.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoRepository extends BaseRepository<Configuracao, Long>  {
}
