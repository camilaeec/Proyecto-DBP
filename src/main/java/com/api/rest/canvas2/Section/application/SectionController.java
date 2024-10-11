package com.api.rest.canvas2.Section.application;

import com.api.rest.canvas2.Section.domain.SectionService;
import com.api.rest.canvas2.Section.dto.SectionRequestDto;
import com.api.rest.canvas2.Section.dto.SectionResponseDto;
import com.api.rest.canvas2.Users.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    // Crear una nueva sección en un curso
    @PostMapping
    public ResponseEntity<SectionResponseDto> createSection(@PathVariable Long courseId, @RequestBody SectionRequestDto sectionRequestDto) {
        SectionResponseDto createdSection = sectionService.createSection(courseId, sectionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSection);
    }

    // Obtener todas las secciones de un curso
    @GetMapping
    public ResponseEntity<List<SectionResponseDto>> getAllSections(@PathVariable Long courseId) {
        List<SectionResponseDto> sections = sectionService.getAllSections(courseId);
        return ResponseEntity.ok(sections);
    }

    // Obtener una sección por ID
    @GetMapping("/{sectionId}")
    public ResponseEntity<SectionResponseDto> getSectionById(@PathVariable Long sectionId) {
        SectionResponseDto section = sectionService.getSectionById(sectionId);
        return ResponseEntity.ok(section);
    }

    // Actualizar una sección por ID
    @PutMapping("/{sectionId}")
    public ResponseEntity<SectionResponseDto> updateSection(@PathVariable Long sectionId, @RequestBody SectionRequestDto sectionRequestDto) {
        SectionResponseDto updatedSection = sectionService.updateSection(sectionId, sectionRequestDto);
        return ResponseEntity.ok(updatedSection);
    }

    // Eliminar una sección por ID
    @DeleteMapping("/{sectionId}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long sectionId) {
        sectionService.deleteSection(sectionId);
        return ResponseEntity.noContent().build();
    }

    // Asignar usuarios a una sección
    @PostMapping("/{sectionId}/users")
    public ResponseEntity<SectionResponseDto> assignUsersToSection(@PathVariable Long sectionId, @RequestBody List<Long> userIds) {
        SectionResponseDto updatedSection = sectionService.assignUsersToSection(sectionId, userIds);
        return ResponseEntity.ok(updatedSection);
    }

    // Obtener todos los usuarios en una sección
    @GetMapping("/{sectionId}/users")
    public ResponseEntity<List<UserResponseDto>> getUsersInSection(@PathVariable Long sectionId) {
        List<UserResponseDto> users = sectionService.getUsersInSection(sectionId);
        return ResponseEntity.ok(users);
    }
}