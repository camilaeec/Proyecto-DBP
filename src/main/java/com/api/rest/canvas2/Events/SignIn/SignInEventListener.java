package com.api.rest.canvas2.Events.SignIn;

import com.api.rest.canvas2.Email.domain.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SignInEventListener {

    private final EmailService emailService;

    public SignInEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void handleSignInEvent(SignInEvent event) {
        try {
            emailService.correoSignIn(event.getEmail(), event.getName(), event.getLastname());
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}