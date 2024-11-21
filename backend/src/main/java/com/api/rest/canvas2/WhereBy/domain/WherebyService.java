package com.api.rest.canvas2.WhereBy.domain;

import com.api.rest.canvas2.Announcement.domain.Announcement;
import com.api.rest.canvas2.Announcement.infrastructure.AnnouncementRepository;
import com.api.rest.canvas2.Email.domain.EmailService;
import com.api.rest.canvas2.Events.WherebyMeeting.WherebyMeetingEvent;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.WhereBy.infrastructure.WherebyMeetingRepository;
import com.api.rest.canvas2.auth.utils.AuthorizationUtils;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import org.json.JSONObject;

@Service
@RequiredArgsConstructor
public class WherebyService {

    @Value("${whereby.api.key}")
    private String wherebyApiKey;

    private final RestTemplate restTemplate;
    private final WherebyMeetingRepository meetingRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthorizationUtils authorizationUtils;

    public WherebyMeeting createMeeting(String roomName, int duration, Long userId, Long sectionId) {
        // Verificar permisos
        if (!(authorizationUtils.isTeacherOrAdmin() || authorizationUtils.isAssistant())) {
            throw new SecurityException("No tienes permisos para crear esta reunión.");
        }

        // Preparar el cuerpo de la solicitud
        JSONObject requestBody = new JSONObject();
        requestBody.put("endDate", LocalDateTime.now().plusMinutes(duration).toString());
        requestBody.put("roomName", roomName);
        requestBody.put("roomMode", "normal");

        // Configurar los headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + wherebyApiKey);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);

        // Realizar la solicitud a la API de Whereby
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.whereby.dev/v1/meetings", request, String.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Error al crear la reunión: " + response.getBody());
        }

        try {
            // Procesar la respuesta
            JSONObject responseBody = new JSONObject(response.getBody());
            String roomUrl = responseBody.getString("roomUrl");

            // Obtener usuario y sección
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));
            Section section = sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada."));

            // Guardar la reunión en la base de datos
            WherebyMeeting meeting = new WherebyMeeting();
            meeting.setRoomName(roomName);
            meeting.setRoomUrl(roomUrl);
            meeting.setStartTime(LocalDateTime.now());
            meeting.setEndTime(LocalDateTime.now().plusMinutes(duration));
            meeting.setUser(user);
            meeting.setSection(section);

            WherebyMeeting savedMeeting = meetingRepository.save(meeting);

            // Publicar el evento de la reunión creada
            notifyMeetingCreation(section.getType(), roomUrl, user.getEmail());

            return savedMeeting;
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la respuesta de Whereby: " + e.getMessage());
        }
    }

    private void notifyMeetingCreation(String sectionName, String roomUrl, String recipientEmail) {
        WherebyMeetingEvent event = new WherebyMeetingEvent(this, sectionName, roomUrl, recipientEmail);
        eventPublisher.publishEvent(event);
    }
}

    /*
        private void createAnnouncement(Section section, String roomUrl) {
        Announcement announcement = new Announcement();
        announcement.setTitle("Nueva reunión programada");
        announcement.setContent("Únete a la reunión usando este enlace: " + roomUrl);
        announcement.setSection(section);
        announcementRepository.save(announcement);
    }

    private void notifyParticipantsByEmail(Section section, String roomUrl) {
        section.getUsers().forEach(user -> {
            emailService.sendMeetingNotification(user.getEmail(), section.getType(), roomUrl);
        });
    }
     */