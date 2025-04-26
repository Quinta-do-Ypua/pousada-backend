package com.senai.pousadabackend.service.quarto;

import com.senai.pousadabackend.entity.Quarto;
import com.senai.pousadabackend.repository.QuartoRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class QuartoServiceProxy extends BaseService<Quarto, Long, QuartoRepository> implements QuartoService {

    public QuartoServiceProxy(QuartoRepository repo) {
        super(repo);
    }

}
