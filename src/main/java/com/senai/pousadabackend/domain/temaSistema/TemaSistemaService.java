package com.senai.pousadabackend.domain.temaSistema;

import com.senai.pousadabackend.core.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class TemaSistemaService extends BaseService<TemaSistema, Long, TemaSistemaRepository> {

    public TemaSistemaService(TemaSistemaRepository repo) {
        super(repo);
    }

}
