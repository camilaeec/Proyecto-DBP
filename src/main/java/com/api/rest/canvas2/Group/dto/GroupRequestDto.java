package com.api.rest.canvas2.Group.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupRequestDto {
    private String name;
    private Long sectionId;
    private List<Long> userIds;
    private Long assignmentId;
}
