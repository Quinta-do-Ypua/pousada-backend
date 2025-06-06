package com.senai.pousadabackend.domain.cliente.service;

import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.email.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceProxy implements ClienteService {

    private final ClienteService delegate;

    private final EmailService emailService;

    public ClienteServiceProxy(@Qualifier("clienteServiceImpl") ClienteService clienteService,
                               EmailService emailService) {
        this.delegate = clienteService;
        this.emailService = emailService;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        return delegate.salvar(cliente);
    }

    @Override
    public Cliente buscarPorId(Long aLong) {
        return delegate.buscarPorId(aLong);
    }

    @Override
    public Cliente excluir(Long id) {
        Cliente cliente = delegate.buscarPorId(id);
        emailService.enviar("Inativação de perfil", "Seu perfil foi inativado", cliente);
        return delegate.excluir(id);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<Cliente> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Cliente> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

}
