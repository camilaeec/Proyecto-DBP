package com.api.rest.canvas2.announcement;

import com.api.rest.canvas2.Announcement.domain.Announcement;
import com.api.rest.canvas2.Announcement.dto.AnnouncementResponseDto;
import com.api.rest.canvas2.Announcement.dto.AnnouncementRequestDto;
import com.api.rest.canvas2.Announcement.infrastructure.AnnouncementRepository;
import com.api.rest.canvas2.Announcement.domain.AnnouncementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnnouncementServiceTest {

    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private AnnouncementService announcementService;

    private Announcement announcement;

    private AnnouncementResponseDto announcementResponseDto;

    private AnnouncementRequestDto announcementRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        announcement = new Announcement();
        announcement.setId(1L);
        announcement.setTitle("Test Announcement");
        announcement.setContent("This is a test content.");

        announcementRequestDto = new AnnouncementRequestDto();
        announcementRequestDto.setTitle("Test Announcement");
        announcementRequestDto.setContent("This is a test content.");
        announcementRequestDto.setUserId(1L);
        announcementRequestDto.setSectionId(1L);

        announcementResponseDto = new AnnouncementResponseDto();
        announcementResponseDto.setId(1L);
        announcementResponseDto.setTitle("Test Announcement");
        announcementResponseDto.setContent("This is a test content.");
        announcementResponseDto.setUserName("John Doe");
        announcementResponseDto.setSectionName("Section A");
    }

    @Test
    void testGetAnnouncementById_Success() {
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));

        AnnouncementResponseDto result = announcementService.getAnnouncementById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Announcement", result.getTitle());
        assertEquals("This is a test content.", result.getContent());
    }

    @Test
    void testGetAnnouncementById_NotFound() {
        when(announcementRepository.findById(1L)).thenReturn(Optional.empty());

        AnnouncementResponseDto result = announcementService.getAnnouncementById(1L);
        assertNull(result);
    }

    @Test
    void testCreateAnnouncement_Success() {
        when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);

        AnnouncementResponseDto result = announcementService.createAnnouncement(announcementRequestDto);
        assertNotNull(result);
        assertEquals("Test Announcement", result.getTitle());
        assertEquals("This is a test content.", result.getContent());
    }
}
