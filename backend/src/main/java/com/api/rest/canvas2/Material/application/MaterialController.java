package com.api.rest.canvas2.Material.application;

import com.api.rest.canvas2.Material.domain.MaterialService;
import com.api.rest.canvas2.Material.dto.MaterialRequestDto;
import com.api.rest.canvas2.Material.dto.MaterialResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping
    public ResponseEntity<MaterialResponseDto> createMaterial(@RequestBody MaterialRequestDto materialRequestDto) {
        MaterialResponseDto createdMaterial = materialService.createMaterial(materialRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMaterial);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_ASSISTANT', 'ROLE_STUDENT')")
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<MaterialResponseDto>> getMaterialsBySection(@PathVariable Long sectionId) {
        List<MaterialResponseDto> materials = materialService.getMaterialsBySection(sectionId);
        return ResponseEntity.ok(materials);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_ASSISTANT', 'ROLE_STUDENT')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MaterialResponseDto>> getMaterialsByUser(@PathVariable Long userId) {
        List<MaterialResponseDto> materials = materialService.getMaterialsByUser(userId);
        return ResponseEntity.ok(materials);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponseDto> updateMaterial(
            @PathVariable Long id, @RequestBody MaterialRequestDto materialRequestDto) {
        MaterialResponseDto updatedMaterial = materialService.updateMaterial(id, materialRequestDto);
        return ResponseEntity.ok(updatedMaterial);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}

