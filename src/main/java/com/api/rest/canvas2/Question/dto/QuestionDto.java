package com.api.rest.canvas2.Question.dto;

import com.api.rest.canvas2.Answer.dto.AnswerDto;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private String content;
    private Boolean isMultipleChoice;
    private Integer points;
    private List<AnswerDto> answers;
}
