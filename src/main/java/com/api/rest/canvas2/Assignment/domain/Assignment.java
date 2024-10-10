package com.api.rest.canvas2.Assignment.domain;

import com.api.rest.canvas2.Grades.domain.Grades;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Users.domain.User;
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
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;

    private Boolean isGroupWork;

    @ManyToOne
    private Section section;

    @ManyToMany
    private List<User> users;

    @OneToMany
    private List<Grades> grades;
}
