package com.senai.pousadabackend.domain.endereco;

import com.senai.pousadabackend.core.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService extends BaseService<Endereco, Long, EnderecoRepository> {

    public EnderecoService(EnderecoRepository repo) {
        super(repo);
    }

}
