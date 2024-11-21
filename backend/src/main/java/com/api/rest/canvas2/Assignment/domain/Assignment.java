package com.api.rest.canvas2.Assignment.domain;

import com.api.rest.canvas2.Grades.domain.Grade;
import com.api.rest.canvas2.Group.domain.Group;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Users.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String title;

    @Size(max = 250)
    private String description;

    private LocalDate dueDate;

    @NotNull
    private Boolean isGroupWork;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    private String assignmentLink;

    private String submissionLink;

    @ManyToMany
    @JoinTable(
            name = "assignment_user",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignedUsers;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Grade> grades;

    @ManyToMany
    @JoinTable(
            name = "assignment_group",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;
}
