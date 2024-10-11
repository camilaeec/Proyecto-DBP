package com.api.rest.canvas2.Section.dto;

import lombok.Data;

@Data
public class SectionRequestDto {
    private String name;
    private String type;  // "Teoría" o "Laboratorio"
}