package com.api.rest.canvas2.Events.ZoomMeeting;

import com.api.rest.canvas2.Email.domain.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZoomMeetingEventListener {

    private final EmailService emailService;

    @Async
    @EventListener
    public void handleZoomMeetingEvent(ZoomMeetingEvent event) throws MessagingException {
        emailService.correoZoomMeeting(
                event.getRecipientEmail(),
                event.getCourseName(),
                event.getMeetingDate(),
                event.getZoomLink()
        );
    }
}