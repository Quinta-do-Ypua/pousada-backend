package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.entity.Cliente;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clientes")
public class ClienteController extends BaseController<Cliente, Long> {

    public ClienteController(@Qualifier("clienteServiceImpl") BaseService<Cliente, Long> baseService) {
        super(baseService);
    }

}
