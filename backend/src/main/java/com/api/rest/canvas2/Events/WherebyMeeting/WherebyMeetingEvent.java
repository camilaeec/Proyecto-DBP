package com.api.rest.canvas2.Events.WherebyMeeting;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class WherebyMeetingEvent extends ApplicationEvent {

    private final String sectionName;
    private final String roomUrl;
    private final String recipientEmail;

    public WherebyMeetingEvent(Object source, String sectionName, String roomUrl, String recipientEmail) {
        super(source);
        this.sectionName = sectionName;
        this.roomUrl = roomUrl;
        this.recipientEmail = recipientEmail;
    }
}
