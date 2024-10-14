package com.api.rest.canvas2.course;
import com.api.rest.canvas2.Course.application.CourseController;
import com.api.rest.canvas2.Course.domain.CourseService;
import com.api.rest.canvas2.Course.dto.CourseResponseDto;
import com.api.rest.canvas2.Course.dto.CourseRequestDto;
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
@WebMvcTest(CourseController.class)
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;
    private CourseResponseDto courseResponseDto;
    private CourseRequestDto courseRequestDto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseResponseDto = new CourseResponseDto();
        courseResponseDto.setId(1L);
        courseResponseDto.setName("Software Engineering");
        courseResponseDto.setDescription("A comprehensive course on software engineering.");
        courseRequestDto = new CourseRequestDto();
        courseRequestDto.setName("Software Engineering");
        courseRequestDto.setDescription("A comprehensive course on software engineering.");
    }
    @Test
    void testGetCourseById_Success() throws Exception {
        when(courseService.getCourseById(1L)).thenReturn(courseResponseDto);
        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Software Engineering"))
                .andExpect(jsonPath("$.description").value("A comprehensive course on software engineering."));
    }
    @Test
    void testCreateCourse_Success() throws Exception {
        when(courseService.createCourse(any(CourseRequestDto.class))).thenReturn(courseResponseDto);
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Software Engineering\", \"description\": \"A comprehensive course on software engineering.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Software Engineering"))
                .andExpect(jsonPath("$.description").value("A comprehensive course on software engineering."));
    }
}