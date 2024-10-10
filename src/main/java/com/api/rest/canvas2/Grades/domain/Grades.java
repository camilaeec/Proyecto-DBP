package com.api.rest.canvas2.Grades.domain;

import com.api.rest.canvas2.Assignment.domain.Assignment;
import com.api.rest.canvas2.Quiz.domain.Quiz;
import com.api.rest.canvas2.Users.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer grade;

    private String feedBack;

    @ManyToOne
    private User user;

    @ManyToOne
    private Assignment assignment;

    @ManyToOne
    private Quiz quiz;
}
