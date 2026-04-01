package com.senai.pousadabackend.domain.temaSistema;

import com.senai.pousadabackend.core.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class TemaSistemaServiceImpl extends BaseService<TemaSistema, Long, TemaSistemaRepository> implements TemaSistemaService {

    public TemaSistemaServiceImpl(TemaSistemaRepository repo) {
        super(repo);
    }

}
