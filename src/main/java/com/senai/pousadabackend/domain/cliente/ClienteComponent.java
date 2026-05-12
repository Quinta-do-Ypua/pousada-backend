package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.infraestructure.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteComponent {

    private final ClienteService delegate;
    private final EmailService emailService;

    public Cliente inativar(Long id) {
        Cliente cliente = delegate.buscarPorId(id);
        emailService.enviar("Inativação de perfil", "Seu perfil foi inativado", cliente);
        return delegate.excluir(id);
    }

}
