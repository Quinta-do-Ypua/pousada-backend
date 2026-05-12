package com.senai.pousadabackend.domain.amenidade;

import com.senai.pousadabackend.core.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class AmenidadeService extends BaseService<Amenidade, Long, AmenidadeRepository> {

    public AmenidadeService(AmenidadeRepository repo) {
        super(repo);
    }

}
