package com.api.rest.canvas2.Material.dto;

import lombok.Data;

@Data
public class MaterialResponseDto {
    private Long id;
    private String title;
    private String description;
    private String fileUrl;
    private String userName;
    private String sectionName;
}
