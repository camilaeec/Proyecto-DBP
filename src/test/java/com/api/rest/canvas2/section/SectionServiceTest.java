package com.api.rest.canvas2.section;

import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Section.dto.SectionRequestDto;
import com.api.rest.canvas2.Section.dto.SectionResponseDto;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
import com.api.rest.canvas2.Section.domain.SectionService;
import com.api.rest.canvas2.Course.domain.Course;
import com.api.rest.canvas2.Course.infrastructure.CourseRepository;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SectionServiceTest {

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private SectionService sectionService;

    private Section section;

    private Course course;

    private SectionResponseDto sectionResponseDto;

    private SectionRequestDto sectionRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course();
        course.setId(1L);
        course.setName("Software Engineering");

        section = new Section();
        section.setId(1L);
        section.setType("Lecture");
        section.setCourse(course);

        sectionRequestDto = new SectionRequestDto();
        sectionRequestDto.setType("Lecture");

        sectionResponseDto = new SectionResponseDto();
        sectionResponseDto.setId(1L);
        sectionResponseDto.setType("Lecture");
    }

    @Test
    void testGetSectionById_Success() {
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));

        SectionResponseDto result = sectionService.getSectionById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Lecture", result.getType());
    }

    @Test
    void testGetSectionById_NotFound() {
        when(sectionRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            sectionService.getSectionById(1L);
        });
        assertEquals("Section not found with id: 1", exception.getMessage());
    }

    @Test
    void testCreateSection_Success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(sectionRepository.save(any(Section.class))).thenReturn(section);

        SectionResponseDto result = sectionService.createSection(1L, sectionRequestDto);
        assertNotNull(result);
        assertEquals("Lecture", result.getType());
    }
}
