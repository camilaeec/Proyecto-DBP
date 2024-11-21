package com.api.rest.canvas2.section;

import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
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
public class SectionRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private SectionRepository sectionRepository;

    @Test
    void testSaveSection() {
        Section section = new Section();
        section.setType("Lecture");

        Section savedSection = sectionRepository.save(section);

        assertNotNull(savedSection.getId());
        assertEquals("Lecture", savedSection.getType());
    }

    @Test
    void testFindById_Success() {
        Section section = new Section();
        section.setType("Lecture");
        section = sectionRepository.save(section);

        Optional<Section> foundSection = sectionRepository.findById(section.getId());

        assertTrue(foundSection.isPresent());
        assertEquals("Lecture", foundSection.get().getType());
    }

    @Test
    void testDeleteSection() {
        Section section = new Section();
        section.setType("Lecture");
        section = sectionRepository.save(section);

        sectionRepository.delete(section);

        Optional<Section> deletedSection = sectionRepository.findById(section.getId());
        assertFalse(deletedSection.isPresent());
    }
}
