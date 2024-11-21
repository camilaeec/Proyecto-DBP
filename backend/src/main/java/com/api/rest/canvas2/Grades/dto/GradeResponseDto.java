package com.api.rest.canvas2.Grades.dto;

import lombok.Data;

@Data
public class GradeResponseDto {
    private Long id;
    private Integer grade;
    private String feedback;
    private String studentName;
    private String assignmentTitle;
    private String quizTitle;
    private String groupName;
}
