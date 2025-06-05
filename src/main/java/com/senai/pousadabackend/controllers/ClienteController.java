package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.cliente.ClienteDTO;
import com.senai.pousadabackend.domain.cliente.ClienteMapper;
import com.senai.pousadabackend.domain.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clientes")
public class ClienteController extends BaseController<Cliente, ClienteDTO, Long, ClienteMapper> {


    public ClienteController(ClienteMapper mapper,
                             @Qualifier("clienteServiceProxy") ClienteService clienteService) {
        super(mapper, clienteService);
    }
}
