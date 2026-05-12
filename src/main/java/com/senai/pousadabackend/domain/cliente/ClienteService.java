package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.core.base.BaseService;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends BaseService<Cliente, Long, ClienteRepository> {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        super(repo);
        this.repo = repo;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        validarViolacaoDeUnique(cliente);
        return super.salvar(cliente);
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
