package com.api.rest.canvas2.quiz;

import com.api.rest.canvas2.Quiz.domain.Quiz;
import com.api.rest.canvas2.Quiz.dto.QuizRequestDto;
import com.api.rest.canvas2.Quiz.dto.QuizResponseDto;
import com.api.rest.canvas2.Quiz.infrastructure.QuizRepository;
import com.api.rest.canvas2.Quiz.domain.QuizService;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;

    private Section section;

    private QuizRequestDto quizRequestDto;

    private QuizResponseDto quizResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        section = new Section();
        section.setId(1L);
        section.setType("Lecture");

        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Quiz 1");
        quiz.setSection(section);

        quizRequestDto = new QuizRequestDto();
        quizRequestDto.setTitle("Quiz 1");
        quizRequestDto.setSectionId(1L);

        quizResponseDto = new QuizResponseDto();
        quizResponseDto.setId(1L);
        quizResponseDto.setTitle("Quiz 1");
    }

    @Test
    void testGetQuizById_Success() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        QuizResponseDto result = quizService.getQuizById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Quiz 1", result.getTitle());
    }

    @Test
    void testGetQuizById_NotFound() {
        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            quizService.getQuizById(1L);
        });
        assertEquals("Quiz not found with id: 1", exception.getMessage());
    }

    @Test
    void testCreateQuiz_Success() {
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        QuizResponseDto result = quizService.createQuiz(quizRequestDto);
        assertNotNull(result);
        assertEquals("Quiz 1", result.getTitle());
    }
}
