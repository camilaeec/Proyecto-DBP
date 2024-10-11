package com.api.rest.canvas2.Quiz.application;

import com.api.rest.canvas2.Quiz.domain.QuizService;
import com.api.rest.canvas2.Quiz.dto.QuizRequestDto;
import com.api.rest.canvas2.Quiz.dto.QuizResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections/{sectionId}/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // Crear un nuevo quiz
    @PostMapping
    public ResponseEntity<QuizResponseDto> createQuiz(@PathVariable Long sectionId, @RequestBody QuizRequestDto quizRequestDto) {
        quizRequestDto.setSectionId(sectionId);  // Asociar la sección al quiz
        QuizResponseDto createdQuiz = quizService.createQuiz(quizRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    // Obtener todos los quizzes de una sección
    @GetMapping
    public ResponseEntity<List<QuizResponseDto>> getQuizzesBySection(@PathVariable Long sectionId) {
        List<QuizResponseDto> quizzes = quizService.getQuizzesBySection(sectionId);
        return ResponseEntity.ok(quizzes);
    }

    // Obtener un quiz por ID
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> getQuizById(@PathVariable Long quizId) {
        QuizResponseDto quiz = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quiz);
    }

    // Actualizar un quiz
    @PutMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> updateQuiz(@PathVariable Long quizId, @RequestBody QuizRequestDto quizRequestDto) {
        QuizResponseDto updatedQuiz = quizService.updateQuiz(quizId, quizRequestDto);
        return ResponseEntity.ok(updatedQuiz);
    }

    // Eliminar un quiz
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }
}
