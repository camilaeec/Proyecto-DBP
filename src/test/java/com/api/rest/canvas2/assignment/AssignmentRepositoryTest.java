package com.api.rest.canvas2.assignment;

import com.api.rest.canvas2.Assignment.domain.Assignment;
import com.api.rest.canvas2.Assignment.infrastructure.AssignmentRepository;
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
public class AssignmentRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Test
    void testSaveAssignment() {
        Assignment assignment = new Assignment();
        assignment.setTitle("Test Assignment");
        assignment.setDescription("This is a test assignment.");

        Assignment savedAssignment = assignmentRepository.save(assignment);

        assertNotNull(savedAssignment.getId());
        assertEquals("Test Assignment", savedAssignment.getTitle());
    }

    @Test
    void testFindById_Success() {
        Assignment assignment = new Assignment();
        assignment.setTitle("Test Assignment");
        assignment.setDescription("This is a test assignment.");
        assignment = assignmentRepository.save(assignment);

        Optional<Assignment> foundAssignment = assignmentRepository.findById(assignment.getId());

        assertTrue(foundAssignment.isPresent());
        assertEquals("Test Assignment", foundAssignment.get().getTitle());
    }

    @Test
    void testDeleteAssignment() {
        Assignment assignment = new Assignment();
        assignment.setTitle("Test Assignment");
        assignment.setDescription("This is a test assignment.");
        assignment = assignmentRepository.save(assignment);

        assignmentRepository.delete(assignment);

        Optional<Assignment> deletedAssignment = assignmentRepository.findById(assignment.getId());
        assertFalse(deletedAssignment.isPresent());
    }
}
