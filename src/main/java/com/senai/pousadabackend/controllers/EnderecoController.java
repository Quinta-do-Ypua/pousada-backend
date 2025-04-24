package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.entity.Endereco;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enderecos")
public class EnderecoController extends BaseController<Endereco, Long> {

    public EnderecoController(@Qualifier("enderecoServiceImpl") BaseService<Endereco, Long> baseService) {
        super(baseService);
    }

}
