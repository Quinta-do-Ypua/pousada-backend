package com.senai.pousadabackend.domain.amenidade.service;

import com.senai.pousadabackend.domain.amenidade.Amenidade;
import com.senai.pousadabackend.domain.amenidade.AmenidadeRepository;
import com.senai.pousadabackend.core.BaseService;
import org.springframework.stereotype.Service;

@Service
public class AmenidadeServiceImpl extends BaseService<Amenidade, Long, AmenidadeRepository> implements AmenidadeService {

    public AmenidadeServiceImpl(AmenidadeRepository repo) {
        super(repo);
    }

}
