package com.api.rest.canvas2.Announcement.application;

import com.api.rest.canvas2.Announcement.domain.AnnouncementService;
import com.api.rest.canvas2.Announcement.dto.AnnouncementRequestDto;
import com.api.rest.canvas2.Announcement.dto.AnnouncementResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<AnnouncementResponseDto> createAnnouncement(@RequestBody AnnouncementRequestDto announcementRequestDto) {
        AnnouncementResponseDto createdAnnouncement = announcementService.createAnnouncement(announcementRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnouncement);
    }

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<AnnouncementResponseDto>> getAnnouncementsBySection(@PathVariable Long sectionId) {
        List<AnnouncementResponseDto> announcements = announcementService.getAnnouncementsBySection(sectionId);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponseDto> getAnnouncementById(@PathVariable Long announcementId) {
        AnnouncementResponseDto announcement = announcementService.getAnnouncementById(announcementId);
        return ResponseEntity.ok(announcement);
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @PutMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponseDto> updateAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementRequestDto announcementRequestDto) {
        AnnouncementResponseDto updatedAnnouncement = announcementService.updateAnnouncement(announcementId, announcementRequestDto);
        return ResponseEntity.ok(updatedAnnouncement);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{announcementId}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long announcementId) {
        announcementService.deleteAnnouncement(announcementId);
        return ResponseEntity.noContent().build();
    }
}

