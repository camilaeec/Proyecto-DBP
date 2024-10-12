package com.api.rest.canvas2.Group.domain;

import com.api.rest.canvas2.Assignment.domain.Assignment;
import com.api.rest.canvas2.Assignment.infrastructure.AssignmentRepository;
import com.api.rest.canvas2.Grades.dto.GradeResponseDto;
import com.api.rest.canvas2.Group.dto.GroupRequestDto;
import com.api.rest.canvas2.Group.dto.GroupResponseDto;
import com.api.rest.canvas2.Group.infrastructure.GroupRepository;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.auth.utils.AuthorizationUtils;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;
    private final AssignmentRepository assignmentRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository,
                        SectionRepository sectionRepository, AssignmentRepository assignmentRepository,
                        ModelMapper modelMapper, AuthorizationUtils authorizationUtils) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.assignmentRepository = assignmentRepository;
        this.modelMapper = modelMapper;
        this.authorizationUtils = authorizationUtils;
    }

    public GroupResponseDto createGroup(GroupRequestDto groupRequestDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can create groups.");
        }

        Section section = sectionRepository.findById(groupRequestDto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + groupRequestDto.getSectionId()));

        List<User> users = userRepository.findAllById(groupRequestDto.getUserIds());

        Group group = new Group();
        group.setName(groupRequestDto.getName());
        group.setSection(section);
        group.setUsers(users);

        if (groupRequestDto.getAssignmentId() != null) {
            Assignment assignment = assignmentRepository.findById(groupRequestDto.getAssignmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + groupRequestDto.getAssignmentId()));
            group.setAssignment(assignment);
        }

        Group savedGroup = groupRepository.save(group);
        return mapToResponseDto(savedGroup);
    }


    public List<GroupResponseDto> getGroupsBySection(Long sectionId) {
        List<Group> groups = groupRepository.findBySectionId(sectionId);
        return groups.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public GroupResponseDto getGroupById(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));
        return mapToResponseDto(group);
    }

    public GroupResponseDto updateGroup(Long groupId, GroupRequestDto groupRequestDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));

        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can update groups.");
        }

        List<User> users = userRepository.findAllById(groupRequestDto.getUserIds());
        group.setUsers(users);

        if (groupRequestDto.getAssignmentId() != null) {
            Assignment assignment = assignmentRepository.findById(groupRequestDto.getAssignmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + groupRequestDto.getAssignmentId()));
            group.setAssignment(assignment);
        }

        Group updatedGroup = groupRepository.save(group);
        return mapToResponseDto(updatedGroup);
    }

    public void deleteGroup(Long groupId) {
        if (!authorizationUtils.isAdmin()) {
            throw new SecurityException("Only admins can delete groups.");
        }

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));
        groupRepository.delete(group);
    }

    public GroupResponseDto joinGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (groupRepository.existsByUsersIdAndAssignmentId(userId, group.getAssignment().getId())) {
            throw new IllegalStateException("User is already in a group for this assignment.");
        }

        if (group.getUsers().size() >= group.getMaxSize()) {
            throw new IllegalStateException("Group is already full.");
        }

        group.getUsers().add(user);
        Group updatedGroup = groupRepository.save(group);

        return mapToResponseDto(updatedGroup);
    }

    public void leaveGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!group.getUsers().contains(user)) {
            throw new IllegalStateException("User is not part of this group.");
        }

        group.getUsers().remove(user);
        groupRepository.save(group);
    }

    private GroupResponseDto mapToResponseDto(Group group) {
        GroupResponseDto responseDto = modelMapper.map(group, GroupResponseDto.class);
        responseDto.setSectionName(group.getSection().getType());
        responseDto.setMemberNames(group.getUsers().stream().map(User::getName).collect(Collectors.toList()));

        if (group.getAssignment() != null) {
            responseDto.setAssignmentTitle(group.getAssignment().getTitle());
        }

        if (group.getGrades() != null) {
            responseDto.setGrades(group.getGrades().stream()
                    .map(grade -> modelMapper.map(grade, GradeResponseDto.class))
                    .collect(Collectors.toList()));
        }

        return responseDto;
    }

}
