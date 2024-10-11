package com.api.rest.canvas2.Assistant.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssistantRequestDto {
    private Long userId;
    private Boolean isExternal;
    private List<Long> sectionIds;
}
