package com.api.rest.canvas2.Answer.infrastructure;

import com.api.rest.canvas2.Answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
