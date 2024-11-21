package com.api.rest.canvas2.auth.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}