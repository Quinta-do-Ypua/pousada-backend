package com.senai.pousadabackend.service.quarto;

import com.senai.pousadabackend.entity.Quarto;
import com.senai.pousadabackend.repository.QuartoRepository;
import com.senai.pousadabackend.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class QuartoServiceImpl extends BaseServiceImpl<Quarto, Long, QuartoRepository> implements QuartoService {

    public QuartoServiceImpl(QuartoRepository repo) {
        super(repo);
    }

}
