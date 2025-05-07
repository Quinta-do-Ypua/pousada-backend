package com.senai.pousadabackend.service.Complemento;

import com.senai.pousadabackend.entity.Complemento;
import com.senai.pousadabackend.repository.ComplementoRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ComplementoServiceProxy extends BaseService<Complemento, Long, ComplementoRepository> implements ComplementoService {

    public ComplementoServiceProxy(ComplementoRepository repo) {
        super(repo);
    }

}
