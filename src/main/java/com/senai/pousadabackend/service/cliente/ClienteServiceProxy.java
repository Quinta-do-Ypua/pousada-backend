package com.senai.pousadabackend.service.cliente;

import com.senai.pousadabackend.entity.Cliente;
import com.senai.pousadabackend.repository.ClienteRepository;
import com.senai.pousadabackend.service.BaseService;
import com.senai.pousadabackend.service.email.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceProxy extends BaseService<Cliente, Long, ClienteRepository> implements ClienteService {

    private final ClienteService clienteService;

    private final EmailService emailService;

    public ClienteServiceProxy(ClienteRepository repo,
                               @Qualifier("clienteServiceImpl") ClienteService clienteService,
                               EmailService emailService) {
        super(repo);
        this.clienteService = clienteService;
        this.emailService = emailService;
    }

    @Override
    public Cliente excluir(Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        emailService.enviar("Inativação de perfil", "Seu perfil foi inativado", cliente);
        return clienteService.excluir(id);
    }

}
