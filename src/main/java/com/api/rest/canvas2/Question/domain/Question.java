package com.api.rest.canvas2.Question.domain;

import com.api.rest.canvas2.Answer.domain.Answer;
import com.api.rest.canvas2.Quiz.domain.Quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Type type;

    private Boolean isMultipleChoice;

    @ManyToOne
    private Quiz quiz;

    @OneToMany
    private List<Answer> answers;
}
