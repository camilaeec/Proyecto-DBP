package com.api.rest.canvas2.Grades.dto;

import lombok.Data;

@Data
public class GradeRequestDto {
    private Integer grade;
    private String feedback;
    private Long userId;
    private Long assignmentId;
    private Long quizId;
    private Long groupId;
}
