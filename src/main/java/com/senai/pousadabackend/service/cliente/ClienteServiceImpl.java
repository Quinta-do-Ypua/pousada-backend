package com.senai.pousadabackend.service.cliente;

import com.senai.pousadabackend.entity.Cliente;
import com.senai.pousadabackend.repository.ClienteRepository;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends BaseService<Cliente, Long, ClienteRepository> implements ClienteService {

    public ClienteServiceImpl(ClienteRepository repo) {
        super(repo);
    }

}
