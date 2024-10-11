package com.api.rest.canvas2.Users.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private Long id;

    private String name;

    private String lastname;

    private String email;

    private String role;

    private String profilePicture;

    private List<String> grades;
}
