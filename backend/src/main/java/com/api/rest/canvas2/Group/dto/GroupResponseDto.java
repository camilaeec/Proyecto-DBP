package com.api.rest.canvas2.Group.dto;

import com.api.rest.canvas2.Grades.dto.GradeResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class GroupResponseDto {
    private Long id;
    private String name;
    private String sectionName;
    private List<String> memberNames;
    private String assignmentTitle;
    private List<GradeResponseDto> grades;
}
