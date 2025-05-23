package com.senai.pousadabackend.domain.endereco.service;

import com.senai.pousadabackend.domain.endereco.Endereco;
import com.senai.pousadabackend.repository.EnderecoRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl extends BaseService<Endereco, Long, EnderecoRepository> implements EnderecoService {

    public EnderecoServiceImpl(EnderecoRepository repo) {
        super(repo);
    }

}
