package com.api.rest.canvas2.Material.dto;

import lombok.Data;

@Data
public class MaterialRequestDto {
    private String title;
    private String description;
    private String fileUrl;
    private Long userId;
    private Long sectionId;
}
