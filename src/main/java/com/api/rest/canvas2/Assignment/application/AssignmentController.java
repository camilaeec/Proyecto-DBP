package com.api.rest.canvas2.Assignment.application;

import com.api.rest.canvas2.Assignment.domain.AssignmentService;
import com.api.rest.canvas2.Assignment.dto.AssignmentRequestDto;
import com.api.rest.canvas2.Assignment.dto.AssignmentResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections/{sectionId}/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<AssignmentResponseDto> createAssignment(@PathVariable Long sectionId, @RequestBody AssignmentRequestDto assignmentRequestDto) {
        assignmentRequestDto.setSectionId(sectionId);
        AssignmentResponseDto createdAssignment = assignmentService.createAssignment(assignmentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
    }

    @GetMapping
    public ResponseEntity<List<AssignmentResponseDto>> getAssignmentsBySection(@PathVariable Long sectionId) {
        List<AssignmentResponseDto> assignments = assignmentService.getAssignmentsBySection(sectionId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponseDto> getAssignmentById(@PathVariable Long assignmentId) {
        AssignmentResponseDto assignment = assignmentService.getAssignmentById(assignmentId);
        return ResponseEntity.ok(assignment);
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponseDto> updateAssignment(@PathVariable Long assignmentId, @RequestBody AssignmentRequestDto assignmentRequestDto) {
        AssignmentResponseDto updatedAssignment = assignmentService.updateAssignment(assignmentId, assignmentRequestDto);
        return ResponseEntity.ok(updatedAssignment);
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.noContent().build();
    }
}
