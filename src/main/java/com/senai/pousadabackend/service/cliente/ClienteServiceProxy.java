package com.senai.pousadabackend.service.cliente;

import com.senai.pousadabackend.entity.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceProxy implements ClienteService {

    private final ClienteServiceImpl clienteService;

    public ClienteServiceProxy(ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteService.buscarPorId(id);
    }

    @Override
    public Cliente excluir(Long id) {
        return clienteService.excluir(id);
    }

}
