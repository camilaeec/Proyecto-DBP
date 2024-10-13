package com.api.rest.canvas2.assignment;

import com.api.rest.canvas2.Assignment.application.AssignmentController;
import com.api.rest.canvas2.Assignment.domain.AssignmentService;
import com.api.rest.canvas2.Assignment.dto.AssignmentResponseDto;
import com.api.rest.canvas2.Assignment.dto.AssignmentRequestDto;
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

@WebMvcTest(AssignmentController.class)
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssignmentService assignmentService;

    private AssignmentResponseDto assignmentResponseDto;

    private AssignmentRequestDto assignmentRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        assignmentResponseDto = new AssignmentResponseDto();
        assignmentResponseDto.setId(1L);
        assignmentResponseDto.setTitle("Test Assignment");
        assignmentResponseDto.setDescription("This is a test assignment.");

        assignmentRequestDto = new AssignmentRequestDto();
        assignmentRequestDto.setTitle("Test Assignment");
        assignmentRequestDto.setDescription("This is a test assignment.");
    }

    @Test
    void testGetAssignmentById_Success() throws Exception {
        when(assignmentService.getAssignmentById(1L)).thenReturn(assignmentResponseDto);

        mockMvc.perform(get("/assignments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Assignment"))
                .andExpect(jsonPath("$.description").value("This is a test assignment."));
    }

    @Test
    void testCreateAssignment_Success() throws Exception {
        when(assignmentService.createAssignment(any(AssignmentRequestDto.class))).thenReturn(assignmentResponseDto);

        mockMvc.perform(post("/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Assignment\", \"description\": \"This is a test assignment.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Assignment"))
                .andExpect(jsonPath("$.description").value("This is a test assignment."));
    }
}
