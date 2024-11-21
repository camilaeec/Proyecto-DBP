package com.api.rest.canvas2.Events.SignIn;



import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SignInEvent extends ApplicationEvent {
    private final String email;
    private final String name;
    private final String lastname;

    public SignInEvent(Object source, String email, String name, String lastname) {
        super(source);
        this.email = email;
        this.name = name;
        this.lastname = lastname;
    }
}