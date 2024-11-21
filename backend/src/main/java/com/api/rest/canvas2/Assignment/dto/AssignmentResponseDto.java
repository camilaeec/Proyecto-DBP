package com.api.rest.canvas2.Assignment.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AssignmentResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Boolean isGroupWork;
    private String assignmentLink;
    private String submissionLink;
    private String sectionName;
    private List<String> assignedUsers;
}
