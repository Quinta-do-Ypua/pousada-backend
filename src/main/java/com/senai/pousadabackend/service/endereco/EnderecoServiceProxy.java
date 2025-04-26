package com.senai.pousadabackend.service.endereco;

import com.senai.pousadabackend.entity.Endereco;
import com.senai.pousadabackend.repository.EnderecoRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceProxy extends BaseService<Endereco, Long, EnderecoRepository> implements EnderecoService {

    public EnderecoServiceProxy(EnderecoRepository repo) {
        super(repo);
    }

}
