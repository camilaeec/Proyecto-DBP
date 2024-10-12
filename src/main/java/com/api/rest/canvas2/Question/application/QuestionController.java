package com.api.rest.canvas2.Question.application;

import com.api.rest.canvas2.Question.domain.QuestionService;
import com.api.rest.canvas2.Question.dto.QuestionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes/{quizId}/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(
            @PathVariable Long quizId, @RequestBody QuestionDto questionDto) {
        QuestionDto createdQuestion = questionService.createQuestion(quizId, questionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_ASSISTANT', 'ROLE_STUDENT')")
    @GetMapping
    public ResponseEntity<List<QuestionDto>> getQuestionsByQuiz(@PathVariable Long quizId) {
        List<QuestionDto> questions = questionService.getQuestionsByQuiz(quizId);
        return ResponseEntity.ok(questions);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_ASSISTANT', 'ROLE_STUDENT')")
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long questionId) {
        QuestionDto question = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDto> updateQuestion(
            @PathVariable Long questionId, @RequestBody QuestionDto questionDto) {
        QuestionDto updatedQuestion = questionService.updateQuestion(questionId, questionDto);
        return ResponseEntity.ok(updatedQuestion);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}