package com.api.rest.canvas2.Quiz.dto;

import lombok.Data;

@Data
public class QuizAnswerDto {
    private Long questionId;
    private String answer;
}
