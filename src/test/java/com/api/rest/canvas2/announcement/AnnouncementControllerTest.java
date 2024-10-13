package com.api.rest.canvas2.announcement;

import com.api.rest.canvas2.Announcement.application.AnnouncementController;
import com.api.rest.canvas2.Announcement.dto.AnnouncementResponseDto;
import com.api.rest.canvas2.Announcement.dto.AnnouncementRequestDto;
import com.api.rest.canvas2.Announcement.domain.AnnouncementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(AnnouncementController.class)
public class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementService announcementService;

    private AnnouncementResponseDto announcementResponseDto;

    private AnnouncementRequestDto announcementRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        announcementResponseDto = new AnnouncementResponseDto();
        announcementResponseDto.setId(1L);
        announcementResponseDto.setTitle("Test Announcement");
        announcementResponseDto.setContent("This is a test content.");

        announcementRequestDto = new AnnouncementRequestDto();
        announcementRequestDto.setTitle("Test Announcement");
        announcementRequestDto.setContent("This is a test content.");
    }

    @Test
    void testGetAnnouncementById_Success() throws Exception {
        when(announcementService.getAnnouncementById(1L)).thenReturn(announcementResponseDto);

        mockMvc.perform(get("/announcements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Announcement"))
                .andExpect(jsonPath("$.content").value("This is a test content."));
    }

    @Test
    void testCreateAnnouncement_Success() throws Exception {
        when(announcementService.createAnnouncement(any(AnnouncementRequestDto.class))).thenReturn(announcementResponseDto);

        mockMvc.perform(post("/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Announcement\", \"content\": \"This is a test content.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Announcement"))
                .andExpect(jsonPath("$.content").value("This is a test content."));
    }
}
