package com.api.rest.canvas2.course;

import com.api.rest.canvas2.Course.domain.Course;
import com.api.rest.canvas2.Course.infrastructure.CourseRepository;
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
public class CourseRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void testSaveCourse() {
        Course course = new Course();
        course.setName("Software Engineering");
        course.setDescription("A comprehensive course on software engineering.");

        Course savedCourse = courseRepository.save(course);

        assertNotNull(savedCourse.getId());
        assertEquals("Software Engineering", savedCourse.getName());
        assertEquals("A comprehensive course on software engineering.", savedCourse.getDescription());
    }

    @Test
    void testFindById_Success() {
        Course course = new Course();
        course.setName("Software Engineering");
        course.setDescription("A comprehensive course on software engineering.");
        course = courseRepository.save(course);

        Optional<Course> foundCourse = courseRepository.findById(course.getId());

        assertTrue(foundCourse.isPresent());
        assertEquals("Software Engineering", foundCourse.get().getName());
    }

    @Test
    void testDeleteCourse() {
        Course course = new Course();
        course.setName("Software Engineering");
        course.setDescription("A comprehensive course on software engineering.");
        course = courseRepository.save(course);

        courseRepository.delete(course);

        Optional<Course> deletedCourse = courseRepository.findById(course.getId());
        assertFalse(deletedCourse.isPresent());
    }
}
