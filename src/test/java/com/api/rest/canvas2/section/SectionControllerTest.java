package com.api.rest.canvas2.section;

import com.api.rest.canvas2.Section.application.SectionController;
import com.api.rest.canvas2.Section.domain.SectionService;
import com.api.rest.canvas2.Section.dto.SectionRequestDto;
import com.api.rest.canvas2.Section.dto.SectionResponseDto;
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

@WebMvcTest(SectionController.class)
public class SectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SectionService sectionService;

    private SectionResponseDto sectionResponseDto;

    private SectionRequestDto sectionRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sectionResponseDto = new SectionResponseDto();
        sectionResponseDto.setId(1L);
        sectionResponseDto.setType("Lecture");

        sectionRequestDto = new SectionRequestDto();
        sectionRequestDto.setType("Lecture");
    }

    @Test
    void testGetSectionById_Success() throws Exception {
        when(sectionService.getSectionById(1L)).thenReturn(sectionResponseDto);

        mockMvc.perform(get("/courses/1/sections/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("Lecture"));
    }

    @Test
    void testCreateSection_Success() throws Exception {
        when(sectionService.createSection(eq(1L), any(SectionRequestDto.class))).thenReturn(sectionResponseDto);

        mockMvc.perform(post("/courses/1/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"Lecture\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("Lecture"));
    }
}
