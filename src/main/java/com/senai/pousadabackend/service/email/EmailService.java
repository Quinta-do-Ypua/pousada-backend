package com.senai.pousadabackend.service.email;

import com.senai.pousadabackend.domain.cliente.Cliente;

public interface EmailService {

    void enviar(String assunto, String message, Cliente cliete);

}
