package com.api.rest.canvas2.answer;

import com.api.rest.canvas2.Answer.domain.Answer;
import com.api.rest.canvas2.Answer.infrastructure.AnswerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
public class AnswerRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void testSaveAnswer() {
        Answer answer = new Answer();
        answer.setContent("This is a test answer.");
        answer.setIsCorrect(true);

        Answer savedAnswer = answerRepository.save(answer);

        assertNotNull(savedAnswer.getId());
        assertEquals("This is a test answer.", savedAnswer.getContent());
    }

    @Test
    void testFindById_Success() {
        Answer answer = new Answer();
        answer.setContent("This is a test answer.");
        answer.setIsCorrect(true);
        answer = answerRepository.save(answer);

        Optional<Answer> foundAnswer = answerRepository.findById(answer.getId());

        assertTrue(foundAnswer.isPresent());
        assertEquals("This is a test answer.", foundAnswer.get().getContent());
    }

    @Test
    void testDeleteAnswer() {
        Answer answer = new Answer();
        answer.setContent("This is a test answer.");
        answer.setIsCorrect(true);
        answer = answerRepository.save(answer);

        answerRepository.delete(answer);

        Optional<Answer> deletedAnswer = answerRepository.findById(answer.getId());
        assertFalse(deletedAnswer.isPresent());
    }
}

