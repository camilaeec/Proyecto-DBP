package com.api.rest.canvas2.Events.ZoomMeeting;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class ZoomMeetingEvent extends ApplicationEvent {
    private final String courseName;
    private final LocalDateTime meetingDate;
    private final String zoomLink;
    private final String recipientEmail;

    public ZoomMeetingEvent(Object source, String courseName, LocalDateTime meetingDate, String zoomLink, String recipientEmail) {
        super(source);
        this.courseName = courseName;
        this.meetingDate = meetingDate;
        this.zoomLink = zoomLink;
        this.recipientEmail = recipientEmail;
    }
}
