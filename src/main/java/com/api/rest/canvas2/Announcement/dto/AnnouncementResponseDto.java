package com.api.rest.canvas2.Announcement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime datePosted;
    private String userName;
    private String sectionName;
}
