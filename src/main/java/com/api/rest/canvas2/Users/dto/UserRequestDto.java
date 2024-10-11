package com.api.rest.canvas2.Users.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String name;

    private String lastname;

    private String email;

    private String profilePicture;

    private String password;

    private String role;
}
