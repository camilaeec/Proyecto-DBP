package com.api.rest.canvas2.course;

import com.api.rest.canvas2.Course.domain.Course;
import com.api.rest.canvas2.Course.dto.CourseRequestDto;
import com.api.rest.canvas2.Course.dto.CourseResponseDto;
import com.api.rest.canvas2.Course.infrastructure.CourseRepository;
import com.api.rest.canvas2.Course.domain.CourseService;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;

    private CourseResponseDto courseResponseDto;

    private CourseRequestDto courseRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course();
        course.setId(1L);
        course.setName("Software Engineering");
        course.setDescription("A comprehensive course on software engineering.");

        courseRequestDto = new CourseRequestDto();
        courseRequestDto.setName("Software Engineering");
        courseRequestDto.setDescription("A comprehensive course on software engineering.");

        courseResponseDto = new CourseResponseDto();
        courseResponseDto.setId(1L);
        courseResponseDto.setName("Software Engineering");
        courseResponseDto.setDescription("A comprehensive course on software engineering.");
    }

    @Test
    void testGetCourseById_Success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        CourseResponseDto result = courseService.getCourseById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Software Engineering", result.getName());
        assertEquals("A comprehensive course on software engineering.", result.getDescription());
    }

    @Test
    void testGetCourseById_NotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            courseService.getCourseById(1L);
        });
        assertEquals("Course not found with id: 1", exception.getMessage());
    }

    @Test
    void testCreateCourse_Success() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseResponseDto result = courseService.createCourse(courseRequestDto);
        assertNotNull(result);
        assertEquals("Software Engineering", result.getName());
        assertEquals("A comprehensive course on software engineering.", result.getDescription());
    }
}
