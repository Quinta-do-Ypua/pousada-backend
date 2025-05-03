package com.senai.pousadabackend.service.email;

import com.senai.pousadabackend.entity.Cliente;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class EmailServiceImpl implements EmailService {

    private final Email from = new Email("quinta.ypua@gmail.com");
    private final String apiKey = "key";

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
            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());

        } catch (IOException ex) {
            throw new RuntimeException("Erro ao enviar e-mail", ex);
        }
    }
}
