package com.api.rest.canvas2.Assistant.application;

import com.api.rest.canvas2.Assistant.domain.AssistantService;
import com.api.rest.canvas2.Assistant.dto.AssistantRequestDto;
import com.api.rest.canvas2.Assistant.dto.AssistantResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assistants")
public class AssistantController {

    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<AssistantResponseDto> createAssistant(@RequestBody AssistantRequestDto assistantRequestDto) {
        AssistantResponseDto createdAssistant = assistantService.createAssistant(assistantRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssistant);
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<AssistantResponseDto>> getAllAssistants() {
        List<AssistantResponseDto> assistants = assistantService.getAllAssistants();
        return ResponseEntity.ok(assistants);
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AssistantResponseDto> getAssistantById(@PathVariable Long id) {
        AssistantResponseDto assistant = assistantService.getAssistantById(id);
        return ResponseEntity.ok(assistant);
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AssistantResponseDto> updateAssistant(
            @PathVariable Long id, @RequestBody AssistantRequestDto assistantRequestDto) {
        AssistantResponseDto updatedAssistant = assistantService.updateAssistant(id, assistantRequestDto);
        return ResponseEntity.ok(updatedAssistant);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssistant(@PathVariable Long id) {
        assistantService.deleteAssistant(id);
        return ResponseEntity.noContent().build();
    }
}
