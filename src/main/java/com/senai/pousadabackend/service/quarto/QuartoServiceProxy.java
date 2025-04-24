package com.senai.pousadabackend.service.quarto;

import com.senai.pousadabackend.entity.Quarto;
import com.senai.pousadabackend.repository.QuartoRepository;
import com.senai.pousadabackend.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class QuartoServiceProxy extends BaseServiceImpl<Quarto, Long, QuartoRepository> implements QuartoService {

    public QuartoServiceProxy(QuartoRepository repo) {
        super(repo);
    }

}
