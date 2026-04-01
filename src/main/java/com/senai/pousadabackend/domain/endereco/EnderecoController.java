package com.senai.pousadabackend.domain.endereco;

import com.senai.pousadabackend.core.base.BaseController;
import com.senai.pousadabackend.domain.endereco.dto.EnderecoDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enderecos")
public class EnderecoController extends BaseController<Endereco, EnderecoDTO, Long, EnderecoMapper> {


    public EnderecoController(EnderecoMapper mapper,
                              EnderecoService enderecoService) {
        super(mapper, enderecoService);
    }
}
