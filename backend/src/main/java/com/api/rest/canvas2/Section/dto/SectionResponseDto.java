package com.api.rest.canvas2.Section.dto;

import lombok.Data;

import java.util.List;

@Data
public class SectionResponseDto {
    private Long id;
    private String type;
    private List<String> users;
}
