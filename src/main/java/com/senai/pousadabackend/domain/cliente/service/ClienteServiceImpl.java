package com.senai.pousadabackend.domain.cliente.service;

import com.senai.pousadabackend.core.BaseService;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.cliente.ClienteRepository;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends BaseService<Cliente, Long, ClienteRepository> implements ClienteService {

    private final ClienteRepository repo;

    public ClienteServiceImpl(ClienteRepository repo) {
        super(repo);
        this.repo = repo;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        prepararCliente(cliente);
        return super.salvar(cliente);
    }

    private void prepararCliente(Cliente cliente) {
        validarViolacaoDeUnique(cliente);
    }

    private void validarViolacaoDeUnique(Cliente cliente) {

        repo.findByCpf(cliente.getCpf()).ifPresent(c -> {
            if (!c.getId().equals(cliente.getId()))
                throw new RegistroDuplicadoException("Já existe um cliente com esse CPF");
        });

        repo.findByCelular(cliente.getCelular()).ifPresent(c -> {
            if (!c.getId().equals(cliente.getId()))
                throw new RegistroDuplicadoException("Já existe um cliente com esse celular");
        });

        repo.findByEmail(cliente.getEmail()).ifPresent(c -> {
            if (!c.getId().equals(cliente.getId()))
                throw new RegistroDuplicadoException("Já existe um cliente com esse email");
        });
    }



}
