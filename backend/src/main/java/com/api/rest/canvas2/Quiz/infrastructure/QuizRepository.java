package com.api.rest.canvas2.Quiz.infrastructure;

import com.api.rest.canvas2.Quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
