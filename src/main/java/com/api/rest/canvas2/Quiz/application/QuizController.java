package com.api.rest.canvas2.Quiz.application;

import com.api.rest.canvas2.Grades.dto.GradeResponseDto;
import com.api.rest.canvas2.Quiz.domain.QuizService;
import com.api.rest.canvas2.Quiz.dto.QuizAnswerDto;
import com.api.rest.canvas2.Quiz.dto.QuizRequestDto;
import com.api.rest.canvas2.Quiz.dto.QuizResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections/{sectionId}/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping
    public ResponseEntity<QuizResponseDto> createQuiz(
            @PathVariable Long sectionId, @RequestBody QuizRequestDto quizRequestDto) {
        quizRequestDto.setSectionId(sectionId);  // Asociar la sección al quiz
        QuizResponseDto createdQuiz = quizService.createQuiz(quizRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_ASSISTANT', 'ROLE_STUDENT')")
    @GetMapping
    public ResponseEntity<List<QuizResponseDto>> getQuizzesBySection(@PathVariable Long sectionId) {
        List<QuizResponseDto> quizzes = quizService.getQuizzesBySection(sectionId);
        return ResponseEntity.ok(quizzes);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_ASSISTANT', 'ROLE_STUDENT')")
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> getQuizById(@PathVariable Long quizId) {
        QuizResponseDto quiz = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quiz);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PutMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> updateQuiz(
            @PathVariable Long quizId, @RequestBody QuizRequestDto quizRequestDto) {
        QuizResponseDto updatedQuiz = quizService.updateQuiz(quizId, quizRequestDto);
        return ResponseEntity.ok(updatedQuiz);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    @PostMapping("/{quizId}/grade")
    public ResponseEntity<GradeResponseDto> gradeQuiz(
            @PathVariable Long quizId,
            @RequestParam Long userId,
            @RequestBody List<QuizAnswerDto> userAnswers) {
        GradeResponseDto gradeResponse = quizService.gradeQuiz(quizId, userId, userAnswers);
        return ResponseEntity.ok(gradeResponse);
    }
}
