package com.api.rest.canvas2.Announcement.dto;

import lombok.Data;

@Data
public class AnnouncementRequestDto {
    private String title;
    private String content;
    private Long userId;
    private Long sectionId;
}
