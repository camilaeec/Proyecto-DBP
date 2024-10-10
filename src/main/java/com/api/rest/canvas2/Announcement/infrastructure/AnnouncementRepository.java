package com.api.rest.canvas2.Announcement.infrastructure;

import com.api.rest.canvas2.Announcement.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
