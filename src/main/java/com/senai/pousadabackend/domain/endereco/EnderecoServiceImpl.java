package com.senai.pousadabackend.domain.endereco;

import com.senai.pousadabackend.core.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl extends BaseService<Endereco, Long, EnderecoRepository> implements EnderecoService {

    public EnderecoServiceImpl(EnderecoRepository repo) {
        super(repo);
    }

}
