package com.senai.pousadabackend.service.cliente;

import com.senai.pousadabackend.entity.Cliente;
import com.senai.pousadabackend.exceptions.RegistroDuplicadoException;
import com.senai.pousadabackend.repository.ClienteRepository;
import com.senai.pousadabackend.service.BaseService;
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
        if (repo.existsByCelular(cliente.getCelular()))
            throw new RegistroDuplicadoException("Já existe um cliente com esse celular");

        if (repo.existsByCpf(cliente.getCpf()))
            throw new RegistroDuplicadoException("Já existe um cliente com esse CPF");

        if (repo.existsByEmail(cliente.getEmail()))
            throw new RegistroDuplicadoException("Já existe um cliente com esse email");

        if (repo.existsByNome(cliente.getNome()))
            throw new RegistroDuplicadoException("Já existe um cliente com esse nome");

    }

}
