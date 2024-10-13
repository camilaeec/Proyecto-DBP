package com.api.rest.canvas2.Email.domain;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void correoSignIn(String to, String name, String lastname) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("lastname", lastname);

        String process = templateEngine.process("SignInEmail.html", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setText(process, true);
        helper.setSubject("Bienvenido a UTEC++");

        mailSender.send(message);
    }
    @Async
    public void sendMeetingNotification(String to, String sectionName, String roomUrl) {
        Context context = new Context();
        context.setVariable("sectionName", sectionName);
        context.setVariable("roomUrl", roomUrl);

        String process = templateEngine.process("MeetingEmail.html", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name()
            );

            helper.setTo(to);
            helper.setText(process, true);
            helper.setSubject("Nueva Reunión de Clase");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
}
