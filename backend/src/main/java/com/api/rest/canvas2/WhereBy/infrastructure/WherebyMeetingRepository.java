package com.api.rest.canvas2.WhereBy.infrastructure;

import com.api.rest.canvas2.WhereBy.domain.WherebyMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WherebyMeetingRepository extends JpaRepository<WherebyMeeting, Long> {

}
