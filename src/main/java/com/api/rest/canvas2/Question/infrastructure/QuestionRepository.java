package com.api.rest.canvas2.Question.infrastructure;

import com.api.rest.canvas2.Question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
