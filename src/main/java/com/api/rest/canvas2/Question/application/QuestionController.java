package com.api.rest.canvas2.Question.application;

import com.api.rest.canvas2.Question.domain.QuestionService;
import com.api.rest.canvas2.Question.dto.QuestionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes/{quizId}/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // Crear una nueva pregunta en un quiz
    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@PathVariable Long quizId, @RequestBody QuestionDto questionDto) {
        QuestionDto createdQuestion = questionService.createQuestion(quizId, questionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    // Obtener todas las preguntas de un quiz
    @GetMapping
    public ResponseEntity<List<QuestionDto>> getQuestionsByQuiz(@PathVariable Long quizId) {
        List<QuestionDto> questions = questionService.getQuestionsByQuiz(quizId);
        return ResponseEntity.ok(questions);
    }

    // Obtener una pregunta por ID
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long questionId) {
        QuestionDto question = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }

    // Actualizar una pregunta
    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionDto questionDto) {
        QuestionDto updatedQuestion = questionService.updateQuestion(questionId, questionDto);
        return ResponseEntity.ok(updatedQuestion);
    }

    // Eliminar una pregunta
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
