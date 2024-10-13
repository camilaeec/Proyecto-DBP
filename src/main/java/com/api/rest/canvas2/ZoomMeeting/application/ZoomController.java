package com.api.rest.canvas2.ZoomMeeting.application;

import com.api.rest.canvas2.ZoomMeeting.domain.ZoomMeeting;
import com.api.rest.canvas2.ZoomMeeting.domain.ZoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/zoom")
public class ZoomController {

    private final ZoomService zoomService;

    public ZoomController(ZoomService zoomService) {
        this.zoomService = zoomService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/meetings")
    public ResponseEntity<ZoomMeeting> createMeeting(
            @RequestParam String topic,
            @RequestParam String startTime,
            @RequestParam int duration,
            @RequestParam Long userId,
            @RequestParam Long sectionId) {
        try {
            LocalDateTime parsedTime = LocalDateTime.parse(startTime);
            ZoomMeeting meeting = zoomService.createZoomMeeting(topic, parsedTime, duration, userId, sectionId);
            return ResponseEntity.ok(meeting);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
