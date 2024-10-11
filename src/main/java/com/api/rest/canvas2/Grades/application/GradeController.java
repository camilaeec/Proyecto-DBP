package com.api.rest.canvas2.Grades.application;

import com.api.rest.canvas2.Grades.domain.GradeService;
import com.api.rest.canvas2.Grades.dto.GradeRequestDto;
import com.api.rest.canvas2.Grades.dto.GradeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public ResponseEntity<GradeResponseDto> createGrade(@RequestBody GradeRequestDto gradeRequestDto) {
        GradeResponseDto createdGrade = gradeService.createGrade(gradeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrade);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GradeResponseDto>> getGradesByUserAndAssignmentOrQuiz(
            @PathVariable Long userId,
            @RequestParam(required = false) Long assignmentId,
            @RequestParam(required = false) Long quizId) {
        List<GradeResponseDto> grades = gradeService.getGradesByUserAndAssignmentOrQuiz(userId, assignmentId, quizId);
        return ResponseEntity.ok(grades);
    }

    // Obtener una calificación por ID
    @GetMapping("/{gradeId}")
    public ResponseEntity<GradeResponseDto> getGradeById(@PathVariable Long gradeId) {
        GradeResponseDto grade = gradeService.getGradeById(gradeId);
        return ResponseEntity.ok(grade);
    }

    // Actualizar una calificación por ID
    @PutMapping("/{gradeId}")
    public ResponseEntity<GradeResponseDto> updateGrade(@PathVariable Long gradeId, @RequestBody GradeRequestDto gradeRequestDto) {
        GradeResponseDto updatedGrade = gradeService.updateGrade(gradeId, gradeRequestDto);
        return ResponseEntity.ok(updatedGrade);
    }

    // Eliminar una calificación por ID
    @DeleteMapping("/{gradeId}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return ResponseEntity.noContent().build();
    }
}
