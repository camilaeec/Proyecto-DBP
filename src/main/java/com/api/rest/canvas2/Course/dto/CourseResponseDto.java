package com.api.rest.canvas2.Course.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseResponseDto {
    private Long id;

    private String name;
    private String description;

    private List<String> users;
}
