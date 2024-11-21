package com.api.rest.canvas2.Quiz.dto;

import com.api.rest.canvas2.Question.dto.QuestionDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class QuizResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Integer duration;
    private String sectionName;
    private List<QuestionDto> questions;
}
