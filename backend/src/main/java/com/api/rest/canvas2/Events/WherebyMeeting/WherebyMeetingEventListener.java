package com.api.rest.canvas2.Events.WherebyMeeting;

import com.api.rest.canvas2.Email.domain.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WherebyMeetingEventListener {

    private final EmailService emailService;

    @Async
    @EventListener
    public void handleWherebyMeetingEvent(WherebyMeetingEvent event) {
        emailService.sendMeetingNotification(
                event.getRecipientEmail(),
                event.getSectionName(),
                event.getRoomUrl()
        );
    }
}
