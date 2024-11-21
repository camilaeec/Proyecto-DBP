package com.api.rest.canvas2.assignment;

import com.api.rest.canvas2.Assignment.domain.Assignment;
import com.api.rest.canvas2.Assignment.dto.AssignmentRequestDto;
import com.api.rest.canvas2.Assignment.dto.AssignmentResponseDto;
import com.api.rest.canvas2.Assignment.infrastructure.AssignmentRepository;
import com.api.rest.canvas2.Assignment.domain.AssignmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    private Assignment assignment;

    private AssignmentResponseDto assignmentResponseDto;

    private AssignmentRequestDto assignmentRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Test Assignment");
        assignment.setDescription("This is a test assignment.");

        assignmentRequestDto = new AssignmentRequestDto();
        assignmentRequestDto.setTitle("Test Assignment");
        assignmentRequestDto.setDescription("This is a test assignment.");

        assignmentResponseDto = new AssignmentResponseDto();
        assignmentResponseDto.setId(1L);
        assignmentResponseDto.setTitle("Test Assignment");
        assignmentResponseDto.setDescription("This is a test assignment.");
    }

    @Test
    void testGetAssignmentById_Success() {
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));

        AssignmentResponseDto result = assignmentService.getAssignmentById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Assignment", result.getTitle());
        assertEquals("This is a test assignment.", result.getDescription());
    }

    @Test
    void testGetAssignmentById_NotFound() {
        when(assignmentRepository.findById(1L)).thenReturn(Optional.empty());

        AssignmentResponseDto result = assignmentService.getAssignmentById(1L);
        assertNull(result);
    }

    @Test
    void testCreateAssignment_Success() {
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        AssignmentResponseDto result = assignmentService.createAssignment(assignmentRequestDto);
        assertNotNull(result);
        assertEquals("Test Assignment", result.getTitle());
        assertEquals("This is a test assignment.", result.getDescription());
    }
}
