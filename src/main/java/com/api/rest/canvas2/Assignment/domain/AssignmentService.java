package com.api.rest.canvas2.Assignment.domain;

import com.api.rest.canvas2.Assignment.dto.AssignmentRequestDto;
import com.api.rest.canvas2.Assignment.dto.AssignmentResponseDto;
import com.api.rest.canvas2.Assignment.infrastructure.AssignmentRepository;
import com.api.rest.canvas2.Group.domain.Group;
import com.api.rest.canvas2.Group.infrastructure.GroupRepository;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.auth.utils.AuthorizationUtils;
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
    private final GroupRepository groupRepository;
    private final AuthorizationUtils authorizationUtils;

    public AssignmentService(AssignmentRepository assignmentRepository, SectionRepository sectionRepository,
                             UserRepository userRepository, ModelMapper modelMapper, GroupRepository groupRepository,
                             AuthorizationUtils authorizationUtils) {
        this.assignmentRepository = assignmentRepository;
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.groupRepository = groupRepository;
        this.authorizationUtils = authorizationUtils;
    }


    public AssignmentResponseDto createAssignment(AssignmentRequestDto assignmentRequestDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can create assignments.");
        }

        Section section = sectionRepository.findById(assignmentRequestDto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + assignmentRequestDto.getSectionId()));

        Assignment assignment = new Assignment();
        assignment.setTitle(assignmentRequestDto.getTitle());
        assignment.setDescription(assignmentRequestDto.getDescription());
        assignment.setDueDate(assignmentRequestDto.getDueDate());
        assignment.setIsGroupWork(assignmentRequestDto.getIsGroupWork());
        assignment.setSection(section);
        assignment.setAssignmentLink(assignmentRequestDto.getAssignmentLink());

        List<User> sectionUsers = section.getUsers();
        assignment.setAssignedUsers(sectionUsers);

        Assignment savedAssignment = assignmentRepository.save(assignment);

        if (assignmentRequestDto.getIsGroupWork() && assignmentRequestDto.getNumberOfGroups() != null) {
            createGroupsForAssignment(savedAssignment, assignmentRequestDto.getNumberOfGroups(), assignmentRequestDto.getMaxGroupSize());
        }

        return mapToResponseDto(savedAssignment);
    }


    public AssignmentResponseDto submitAssignment(Long assignmentId, String submissionLink) {
        String currentUserEmail = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));

        if (!assignment.getAssignedUsers().contains(user)) {
            throw new SecurityException("You are not assigned to this assignment.");
        }

        assignment.setSubmissionLink(submissionLink);
        Assignment updatedAssignment = assignmentRepository.save(assignment);
        return mapToResponseDto(updatedAssignment);
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

        assignment.setTitle(assignmentRequestDto.getTitle());
        assignment.setDescription(assignmentRequestDto.getDescription());
        assignment.setDueDate(assignmentRequestDto.getDueDate());
        assignment.setIsGroupWork(assignmentRequestDto.getIsGroupWork());
        assignment.setSection(section);

        if (assignmentRequestDto.getIsGroupWork() && assignmentRequestDto.getNumberOfGroups() != null) {
            createGroupsForAssignment(assignment, assignmentRequestDto.getNumberOfGroups(), assignmentRequestDto.getMaxGroupSize());
        }

        Assignment updatedAssignment = assignmentRepository.save(assignment);
        return mapToResponseDto(updatedAssignment);
    }


    public void deleteAssignment(Long id) {
        if (!authorizationUtils.isAdmin()) {
            throw new SecurityException("Only admins can delete assignments.");
        }

        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + id));
        assignmentRepository.delete(assignment);
    }

    private AssignmentResponseDto mapToResponseDto(Assignment assignment) {
        AssignmentResponseDto assignmentResponseDto = modelMapper.map(assignment, AssignmentResponseDto.class);
        assignmentResponseDto.setSectionName(assignment.getSection().getType());
        assignmentResponseDto.setAssignedUsers(
                assignment.getAssignedUsers().stream().map(User::getName).collect(Collectors.toList()));
        return assignmentResponseDto;
    }

    private void createGroupsForAssignment(Assignment assignment, int numberOfGroups, int maxGroupSize) {
        for (int i = 1; i <= numberOfGroups; i++) {
            Group group = new Group();
            group.setName("Grupo " + i);
            group.setAssignment(assignment);
            group.setMaxSize(maxGroupSize);
            groupRepository.save(group);
        }
    }
}
