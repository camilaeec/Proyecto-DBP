package com.api.rest.canvas2.Quiz.domain;

import com.api.rest.canvas2.Grades.domain.Grades;
import com.api.rest.canvas2.Question.domain.Question;
import com.api.rest.canvas2.Section.domain.Section;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;

    @ManyToOne
    private Section section;

    @OneToMany
    private List<Question> questions;

    @OneToMany
    private List<Grades> grades;
}
