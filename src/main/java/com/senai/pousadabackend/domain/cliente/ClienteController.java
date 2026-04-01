package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.domain.cliente.dto.ClienteDTO;
import com.senai.pousadabackend.core.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clientes")
public class ClienteController extends BaseController<Cliente, ClienteDTO, Long, ClienteMapper> {


    public ClienteController(ClienteMapper mapper,
                             ClienteService clienteService) {
        super(mapper, clienteService);
    }
}
