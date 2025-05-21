package com.senai.pousadabackend.service.amenidade;

import com.senai.pousadabackend.entity.Amenidade;
import com.senai.pousadabackend.repository.AmenidadeRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class AmenidadeServiceImpl extends BaseService<Amenidade, Long, AmenidadeRepository> implements AmenidadeService {

    public AmenidadeServiceImpl(AmenidadeRepository repo) {
        super(repo);
    }

}
