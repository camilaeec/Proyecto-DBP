package com.api.rest.canvas2.answer;

import com.api.rest.canvas2.Answer.domain.Answer;
import com.api.rest.canvas2.Answer.dto.AnswerDto;
import com.api.rest.canvas2.Answer.infrastructure.AnswerRepository;
import com.api.rest.canvas2.Answer.domain.AnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnswerServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerService answerService;

    private Answer answer;

    private AnswerDto answerDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        answer = new Answer();
        answer.setId(1L);
        answer.setContent("This is a test answer.");
        answer.setIsCorrect(true);

        answerDto = new AnswerDto();
        answerDto.setContent("This is a test answer.");
        answerDto.setIsCorrect(true);
    }

    @Test
    void testGetAnswerById_Success() {
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));

        AnswerDto result = answerService.getAnswerById(1L);
        assertNotNull(result);
        assertEquals("This is a test answer.", result.getContent());
    }

    @Test
    void testGetAnswerById_NotFound() {
        when(answerRepository.findById(1L)).thenReturn(Optional.empty());

        AnswerDto result = answerService.getAnswerById(1L);
        assertNull(result);
    }

    @Test
    void testCreateAnswer_Success() {
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        AnswerDto result = answerService.createAnswer(1L, answerDto);
        assertNotNull(result);
        assertEquals("This is a test answer.", result.getContent());
        assertTrue(result.getIsCorrect());
    }
}
