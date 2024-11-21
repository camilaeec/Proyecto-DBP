package com.api.rest.canvas2.auth.dto;

import lombok.Data;

@Data
public class SigninDto {
    private String email;
    private String password;
    private String name;
    private String lastname;
    private String role;
}