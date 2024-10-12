package com.api.rest.canvas2.ZoomMeeting.infrastructure;

import com.api.rest.canvas2.ZoomMeeting.domain.ZoomMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoomMeetingRepository extends JpaRepository<ZoomMeeting, Long> {
}
