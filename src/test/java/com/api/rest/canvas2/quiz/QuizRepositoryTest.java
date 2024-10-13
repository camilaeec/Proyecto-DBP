package com.api.rest.canvas2.quiz;

import com.api.rest.canvas2.Quiz.domain.Quiz;
import com.api.rest.canvas2.Quiz.infrastructure.QuizRepository;
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
public class QuizRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private QuizRepository quizRepository;

    @Test
    void testSaveQuiz() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Quiz 1");

        Quiz savedQuiz = quizRepository.save(quiz);

        assertNotNull(savedQuiz.getId());
        assertEquals("Quiz 1", savedQuiz.getTitle());
    }

    @Test
    void testFindById_Success() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Quiz 1");
        quiz = quizRepository.save(quiz);

        Optional<Quiz> foundQuiz = quizRepository.findById(quiz.getId());

        assertTrue(foundQuiz.isPresent());
        assertEquals("Quiz 1", foundQuiz.get().getTitle());
    }

    @Test
    void testDeleteQuiz() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Quiz 1");
        quiz = quizRepository.save(quiz);

        quizRepository.delete(quiz);

        Optional<Quiz> deletedQuiz = quizRepository.findById(quiz.getId());
        assertFalse(deletedQuiz.isPresent());
    }
}
