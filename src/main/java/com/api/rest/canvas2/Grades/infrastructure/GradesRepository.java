package com.api.rest.canvas2.Grades.infrastructure;

import com.api.rest.canvas2.Grades.domain.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradesRepository extends JpaRepository<Grades, Long> {
}
