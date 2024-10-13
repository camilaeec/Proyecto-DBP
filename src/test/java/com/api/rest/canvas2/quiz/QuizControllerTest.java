package com.api.rest.canvas2.quiz;

import com.api.rest.canvas2.Quiz.application.QuizController;
import com.api.rest.canvas2.Quiz.domain.QuizService;
import com.api.rest.canvas2.Quiz.dto.QuizRequestDto;
import com.api.rest.canvas2.Quiz.dto.QuizResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    private QuizResponseDto quizResponseDto;

    private QuizRequestDto quizRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        quizResponseDto = new QuizResponseDto();
        quizResponseDto.setId(1L);
        quizResponseDto.setTitle("Quiz 1");

        quizRequestDto = new QuizRequestDto();
        quizRequestDto.setTitle("Quiz 1");
    }

    @Test
    void testGetQuizById_Success() throws Exception {
        when(quizService.getQuizById(1L)).thenReturn(quizResponseDto);

        mockMvc.perform(get("/sections/1/quizzes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Quiz 1"));
    }

    @Test
    void testCreateQuiz_Success() throws Exception {
        when(quizService.createQuiz(any(QuizRequestDto.class))).thenReturn(quizResponseDto);

        mockMvc.perform(post("/sections/1/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Quiz 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Quiz 1"));
    }
}
