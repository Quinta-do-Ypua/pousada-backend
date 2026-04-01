package com.senai.pousadabackend.infraestructure.sendgrid;

import com.sendgrid.Method;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.Request;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.email}")
    private String fromEmail;

    public void sendResetPasswordEmail(String toEmail, String token) {
        Mail mail = getMail(toEmail, token);
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Email enviado: " + response.getStatusCode());
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao enviar e-mail", ex);
        }
    }

    private Mail getMail(String toEmail, String token) {
        Email from = new Email(fromEmail);
        String subject = "Redefina sua senha";
        Email to = new Email(toEmail);
        String resetLink = "http://localhost:4200/reset-password?token=" + token;
        Content content = new Content("text/html",
                "<p>Você solicitou redefinição de senha.</p>" +
                        "<p>Clique no link abaixo (válido por 15 minutos):</p>" +
                        "<a href=\"" + resetLink + "\">Redefinir Senha</a>");
        return new Mail(from, subject, to, content);
    }

}
