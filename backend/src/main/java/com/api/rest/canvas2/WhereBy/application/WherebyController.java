package com.api.rest.canvas2.WhereBy.application;

import com.api.rest.canvas2.WhereBy.domain.WherebyMeeting;
import com.api.rest.canvas2.WhereBy.domain.WherebyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/whereby")
public class WherebyController {

    private final WherebyService wherebyService;

    public WherebyController(WherebyService wherebyService) {
        this.wherebyService = wherebyService;
    }

    @PostMapping("/meetings")
    public ResponseEntity<WherebyMeeting> createMeeting(
            @RequestParam String roomName,
            @RequestParam int duration,
            @RequestParam Long userId,
            @RequestParam Long sectionId) {
        try {
            WherebyMeeting meeting = wherebyService.createMeeting(roomName, duration, userId, sectionId);
            return ResponseEntity.ok(meeting);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}