package com.sed.backend.infrastructure.adapters.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailAdapter {

    private final JavaMailSender mailSender;
    private final EmailTemplateService templateService;

    public EmailAdapter(JavaMailSender mailSender, EmailTemplateService templateService) {
        this.mailSender = mailSender;
        this.templateService = templateService;
    }

    public void enviarTextoPlano(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void enviarConTemplate(String to, String subject, String templatePath, Map<String, Object> variables) {
        String contenido = templateService.renderTemplate(templatePath, variables);
        enviarTextoPlano(to, subject, contenido);
    }
}