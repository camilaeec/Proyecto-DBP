package com.api.rest.canvas2.announcement;

import com.api.rest.canvas2.Announcement.domain.Announcement;
import com.api.rest.canvas2.Announcement.infrastructure.AnnouncementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
public class AnnouncementRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Test
    void testSaveAnnouncement() {
        Announcement announcement = new Announcement();
        announcement.setTitle("Test Announcement");
        announcement.setContent("This is a test content.");

        Announcement savedAnnouncement = announcementRepository.save(announcement);

        assertNotNull(savedAnnouncement.getId());
        assertEquals("Test Announcement", savedAnnouncement.getTitle());
    }

    @Test
    void testFindById_Success() {
        Announcement announcement = new Announcement();
        announcement.setTitle("Test Announcement");
        announcement.setContent("This is a test content.");
        announcement = announcementRepository.save(announcement);

        Optional<Announcement> foundAnnouncement = announcementRepository.findById(announcement.getId());

        assertTrue(foundAnnouncement.isPresent());
        assertEquals("Test Announcement", foundAnnouncement.get().getTitle());
    }

    @Test
    void testDeleteAnnouncement() {
        Announcement announcement = new Announcement();
        announcement.setTitle("Test Announcement");
        announcement.setContent("This is a test content.");
        announcement = announcementRepository.save(announcement);

        announcementRepository.delete(announcement);

        Optional<Announcement> deletedAnnouncement = announcementRepository.findById(announcement.getId());
        assertFalse(deletedAnnouncement.isPresent());
    }
}
