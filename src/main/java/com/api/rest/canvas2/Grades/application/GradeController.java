package com.api.rest.canvas2.Grades.application;

import com.api.rest.canvas2.Grades.domain.GradeService;
import com.api.rest.canvas2.Grades.dto.GradeRequestDto;
import com.api.rest.canvas2.Grades.dto.GradeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<GradeResponseDto> createGrade(@RequestBody GradeRequestDto gradeRequestDto) {
        GradeResponseDto createdGrade = gradeService.createGrade(gradeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrade);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @authorizationUtils.isAdminOrResourceOwner(#userId)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GradeResponseDto>> getGradesByUserAndAssignmentOrQuiz(
            @PathVariable Long userId,
            @RequestParam(required = false) Long assignmentId,
            @RequestParam(required = false) Long quizId) {
        List<GradeResponseDto> grades = gradeService.getGradesByUserAndAssignmentOrQuiz(userId, assignmentId, quizId);
        return ResponseEntity.ok(grades);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @authorizationUtils.isAdminOrResourceOwner(#gradeId)")
    @GetMapping("/{gradeId}")
    public ResponseEntity<GradeResponseDto> getGradeById(@PathVariable Long gradeId) {
        GradeResponseDto grade = gradeService.getGradeById(gradeId);
        return ResponseEntity.ok(grade);
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @PutMapping("/{gradeId}")
    public ResponseEntity<GradeResponseDto> updateGrade(
            @PathVariable Long gradeId, @RequestBody GradeRequestDto gradeRequestDto) {
        GradeResponseDto updatedGrade = gradeService.updateGrade(gradeId, gradeRequestDto);
        return ResponseEntity.ok(updatedGrade);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{gradeId}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return ResponseEntity.noContent().build();
    }
}
