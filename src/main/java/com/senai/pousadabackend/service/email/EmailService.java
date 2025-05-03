package com.senai.pousadabackend.service.email;

import com.senai.pousadabackend.entity.Cliente;

public interface EmailService {

    void enviar(String assunto, String message, Cliente cliete);

}
