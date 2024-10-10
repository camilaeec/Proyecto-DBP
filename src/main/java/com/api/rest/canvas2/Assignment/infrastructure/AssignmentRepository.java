package com.api.rest.canvas2.Assignment.infrastructure;

import com.api.rest.canvas2.Assignment.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
