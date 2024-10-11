package com.api.rest.canvas2.Assignment.domain;

import com.api.rest.canvas2.Assignment.dto.AssignmentRequestDto;
import com.api.rest.canvas2.Assignment.dto.AssignmentResponseDto;
import com.api.rest.canvas2.Assignment.infrastructure.AssignmentRepository;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AssignmentService(AssignmentRepository assignmentRepository, SectionRepository sectionRepository,
                             UserRepository userRepository, ModelMapper modelMapper) {
        this.assignmentRepository = assignmentRepository;
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public AssignmentResponseDto createAssignment(AssignmentRequestDto assignmentRequestDto) {
        Section section = sectionRepository.findById(assignmentRequestDto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + assignmentRequestDto.getSectionId()));

        List<User> assignedUsers = userRepository.findAllById(assignmentRequestDto.getAssignedUserIds());

        Assignment assignment = new Assignment();
        assignment.setTitle(assignmentRequestDto.getTitle());
        assignment.setDescription(assignmentRequestDto.getDescription());
        assignment.setDueDate(assignmentRequestDto.getDueDate());
        assignment.setIsGroupWork(assignmentRequestDto.getIsGroupWork());
        assignment.setSection(section);
        assignment.setAssignedUsers(assignedUsers);

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return mapToResponseDto(savedAssignment);
    }

    public List<AssignmentResponseDto> getAssignmentsBySection(Long sectionId) {
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream()
                .filter(assignment -> assignment.getSection().getId().equals(sectionId))
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public AssignmentResponseDto getAssignmentById(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + id));
        return mapToResponseDto(assignment);
    }

    public AssignmentResponseDto updateAssignment(Long id, AssignmentRequestDto assignmentRequestDto) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + id));

        Section section = sectionRepository.findById(assignmentRequestDto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + assignmentRequestDto.getSectionId()));

        List<User> assignedUsers = userRepository.findAllById(assignmentRequestDto.getAssignedUserIds());

        assignment.setTitle(assignmentRequestDto.getTitle());
        assignment.setDescription(assignmentRequestDto.getDescription());
        assignment.setDueDate(assignmentRequestDto.getDueDate());
        assignment.setIsGroupWork(assignmentRequestDto.getIsGroupWork());
        assignment.setSection(section);
        assignment.setAssignedUsers(assignedUsers);

        Assignment updatedAssignment = assignmentRepository.save(assignment);
        return mapToResponseDto(updatedAssignment);
    }

    public void deleteAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + id));
        assignmentRepository.delete(assignment);
    }

    private AssignmentResponseDto mapToResponseDto(Assignment assignment) {
        AssignmentResponseDto assignmentResponseDto = modelMapper.map(assignment, AssignmentResponseDto.class);
        assignmentResponseDto.setSectionName(assignment.getSection().getName());
        assignmentResponseDto.setAssignedUsers(
                assignment.getAssignedUsers().stream().map(User::getName).collect(Collectors.toList()));
        return assignmentResponseDto;
    }
}
