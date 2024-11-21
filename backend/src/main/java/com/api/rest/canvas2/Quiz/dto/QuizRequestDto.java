package com.api.rest.canvas2.Quiz.dto;

import com.api.rest.canvas2.Question.dto.QuestionDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class QuizRequestDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Integer duration;
    private Long sectionId;
    private List<QuestionDto> questions;
    private Integer totalPoints;
}
