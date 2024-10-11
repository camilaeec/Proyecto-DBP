package com.api.rest.canvas2.Section.dto;

import com.api.rest.canvas2.Users.dto.UserResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class SectionDto {
    private String type;
    private List<UserResponseDto> users;
}
