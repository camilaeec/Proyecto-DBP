package com.api.rest.canvas2.answer;
import com.api.rest.canvas2.Answer.application.AnswerController;
import com.api.rest.canvas2.Answer.domain.Answer;
import com.api.rest.canvas2.Answer.domain.AnswerService;
import com.api.rest.canvas2.Answer.dto.AnswerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
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
    void testGetAnswerById_Success() throws Exception {
        when(answerService.getAnswerById(1L)).thenReturn(answerDto);
        mockMvc.perform(get("/answers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("This is a test answer."))
                .andExpect(jsonPath("$.isCorrect").value(true));
    }
    @Test
    void testCreateAnswer_Success() throws Exception {
        when(answerService.createAnswer(any(Long.class), any(AnswerDto.class))).thenReturn(answerDto);
        mockMvc.perform(post("/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"This is a test answer.\", \"isCorrect\": true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("This is a test answer."))
                .andExpect(jsonPath("$.isCorrect").value(true));
    }
}