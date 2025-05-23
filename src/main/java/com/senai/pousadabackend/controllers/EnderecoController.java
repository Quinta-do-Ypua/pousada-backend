package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.endereco.EnderecoDTO;
import com.senai.pousadabackend.domain.endereco.Endereco;
import com.senai.pousadabackend.domain.endereco.EnderecoMapper;
import com.senai.pousadabackend.domain.endereco.service.EnderecoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enderecos")
public class EnderecoController extends BaseController<Endereco, EnderecoDTO, Long, EnderecoMapper> {


    public EnderecoController(EnderecoMapper mapper,
                              @Qualifier("enderecoServiceProxy") EnderecoService enderecoService) {
        super(mapper, enderecoService);
    }
}
