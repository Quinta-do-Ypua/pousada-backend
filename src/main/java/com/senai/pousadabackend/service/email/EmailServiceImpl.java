package com.senai.pousadabackend.service.email;

import com.senai.pousadabackend.entity.Cliente;
import com.senai.pousadabackend.exceptions.FalhaAoEnviarEmailException;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@ConfigurationProperties("sendgrid")
public class EmailServiceImpl implements EmailService {

    @Value("${sendgrid.key}")
    private String apiKey;

    @Value("${sendgrid.email}")
    private String emailFrom;

    private Email from;

    @PostConstruct
    public void init() {
        from = new Email(emailFrom);
    }

    @Override
    public void enviar(String assunto, String message, Cliente cliente) {
        Email to = new Email(cliente.getEmail());

        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, assunto, to, content);
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info("Status: {}", response.getStatusCode());
            log.info("Body: {}", response.getBody());
            log.info("Headers: {}", response.getHeaders());
        } catch (IOException ex) {
            throw new FalhaAoEnviarEmailException(ex);
        }
    }
}
