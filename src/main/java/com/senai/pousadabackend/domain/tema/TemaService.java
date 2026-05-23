package com.senai.pousadabackend.domain.tema;

import com.senai.pousadabackend.core.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class TemaService extends BaseService<Tema, Long, TemaRepository> {

    public TemaService(TemaRepository repo) {
        super(repo);
    }

}