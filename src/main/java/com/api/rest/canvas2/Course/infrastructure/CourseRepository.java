package com.api.rest.canvas2.Course.infrastructure;

import com.api.rest.canvas2.Course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
