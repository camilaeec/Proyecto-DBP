package com.api.rest.canvas2.Assistant.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssistantResponseDto {
    private Long id;
    private Boolean isExternal;
    private String userName;
    private List<String> sectionNames;
}
