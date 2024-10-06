package com.api.rest.canvas2.Mentor.infrastructure;

import com.api.rest.canvas2.Mentor.domain.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
}
