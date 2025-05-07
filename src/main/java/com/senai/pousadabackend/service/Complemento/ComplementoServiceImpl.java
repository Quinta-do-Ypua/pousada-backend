package com.senai.pousadabackend.service.Complemento;

import com.senai.pousadabackend.entity.Complemento;
import com.senai.pousadabackend.repository.ComplementoRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ComplementoServiceImpl extends BaseService<Complemento, Long, ComplementoRepository> implements ComplementoService {

    public ComplementoServiceImpl(ComplementoRepository repo) {
        super(repo);
    }
}
