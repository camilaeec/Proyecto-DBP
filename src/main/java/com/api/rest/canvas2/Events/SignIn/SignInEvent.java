package com.api.rest.canvas2.Events.SignIn;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SignInEvent extends ApplicationEvent {
    private final String email;
    private final String name;

    public SignInEvent(Object source, String email, String name) {
        super(source);
        this.email = email;
        this.name = name;
    }
}