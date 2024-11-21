package com.api.rest.canvas2.Grades.infrastructure;

import com.api.rest.canvas2.Grades.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByUserIdAndAssignmentId(Long userId, Long assignmentId);

    List<Grade> findByUserIdAndQuizId(Long userId, Long quizId);
}
