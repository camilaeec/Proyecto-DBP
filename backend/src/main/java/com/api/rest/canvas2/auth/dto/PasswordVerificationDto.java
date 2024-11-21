package com.api.rest.canvas2.auth.dto;

import lombok.Data;

@Data
public class PasswordVerificationDto {
    private Long userId;
    private String password;
}
