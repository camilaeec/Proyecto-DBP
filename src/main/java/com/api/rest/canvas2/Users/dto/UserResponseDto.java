package com.api.rest.canvas2.Users.dto;

import com.api.rest.canvas2.Users.domain.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private Long id;

    private String name;

    private String lastname;

    private String email;

    private Role role;

    private String profilePicture;

}
