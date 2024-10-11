package com.api.rest.canvas2.Assignment.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AssignmentRequestDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Boolean isGroupWork;
    private Long sectionId;
    private List<Long> assignedUserIds;
}
