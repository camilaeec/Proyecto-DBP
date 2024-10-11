package com.api.rest.canvas2.Answer.application;

import com.api.rest.canvas2.Answer.domain.AnswerService;
import com.api.rest.canvas2.Answer.dto.AnswerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<AnswerDto> createAnswer(@PathVariable Long questionId, @RequestBody AnswerDto answerDto) {
        AnswerDto createdAnswer = answerService.createAnswer(questionId, answerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
    }

    @GetMapping
    public ResponseEntity<List<AnswerDto>> getAnswersByQuestion(@PathVariable Long questionId) {
        List<AnswerDto> answers = answerService.getAnswersByQuestion(questionId);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerDto> getAnswerById(@PathVariable Long answerId) {
        AnswerDto answer = answerService.getAnswerById(answerId);
        return ResponseEntity.ok(answer);
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<AnswerDto> updateAnswer(@PathVariable Long answerId, @RequestBody AnswerDto answerDto) {
        AnswerDto updatedAnswer = answerService.updateAnswer(answerId, answerDto);
        return ResponseEntity.ok(updatedAnswer);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
}
