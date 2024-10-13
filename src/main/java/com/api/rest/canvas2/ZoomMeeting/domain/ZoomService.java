package com.api.rest.canvas2.ZoomMeeting.domain;

import com.api.rest.canvas2.Announcement.domain.Announcement;
import com.api.rest.canvas2.Announcement.infrastructure.AnnouncementRepository;
import com.api.rest.canvas2.Events.ZoomMeeting.ZoomMeetingEvent;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.ZoomMeeting.infrastructure.ZoomMeetingRepository;
import com.api.rest.canvas2.auth.utils.AuthorizationUtils;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ZoomService {

    private final ZoomMeetingRepository zoomMeetingRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;
    private final AnnouncementRepository announcementRepository;
    private final RestTemplate restTemplate;
    private final AuthorizationUtils authorizationUtils;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${zoom.meeting.url}")
    private String zoomMeetingUrl;

    @Value("${zoom.access_token}")
    private String zoomAccessToken;

    public ZoomMeeting createZoomMeeting(String topic, LocalDateTime startTime, int duration, Long userId, Long sectionId) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("No tienes permiso para crear esta reunión.");
        }

        JSONObject json = new JSONObject();
        json.put("topic", topic);
        json.put("type", 2);
        json.put("start_time", startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        json.put("duration", duration);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(zoomAccessToken); // Access Token

        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(zoomMeetingUrl, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create Zoom meeting: " + response.getBody());
        }

        JSONObject responseBody = new JSONObject(response.getBody());
        String zoomLink = responseBody.getString("join_url");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

        ZoomMeeting zoomMeeting = new ZoomMeeting();
        zoomMeeting.setZoomLink(zoomLink);
        zoomMeeting.setScheduledDate(startTime);
        zoomMeeting.setUser(user);
        zoomMeeting.setSection(section);

        ZoomMeeting savedMeeting = zoomMeetingRepository.save(zoomMeeting);

        createAnnouncementForZoomMeeting(savedMeeting);
        createAndNotifyZoomMeeting(section.getType(), startTime, zoomLink, user.getEmail());

        return savedMeeting;
    }

    private void createAnnouncementForZoomMeeting(ZoomMeeting zoomMeeting) {
        Announcement announcement = new Announcement();
        announcement.setTitle("Nueva reunión programada: " + zoomMeeting.getSection().getType());
        announcement.setContent("Enlace para unirse: " + zoomMeeting.getZoomLink());
        announcement.setUser(zoomMeeting.getUser());
        announcement.setSection(zoomMeeting.getSection());
        announcementRepository.save(announcement);
    }

    public void createAndNotifyZoomMeeting(String courseName, LocalDateTime meetingDate, String zoomLink, String recipientEmail) {
        ZoomMeetingEvent event = new ZoomMeetingEvent(this, courseName, meetingDate, zoomLink, recipientEmail);
        eventPublisher.publishEvent(event);
    }
}