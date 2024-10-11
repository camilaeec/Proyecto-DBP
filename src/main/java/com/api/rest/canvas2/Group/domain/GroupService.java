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

    public GroupService(GroupRepository groupRepository, UserRepository userRepository,
                        SectionRepository sectionRepository, AssignmentRepository assignmentRepository,
                        ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.assignmentRepository = assignmentRepository;
        this.modelMapper = modelMapper;
    }

    public GroupResponseDto createGroup(GroupRequestDto groupRequestDto) {
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
            group.setAssignments(Collections.singletonList(assignment));
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

        List<User> users = userRepository.findAllById(groupRequestDto.getUserIds());
        group.setUsers(users);

        if (groupRequestDto.getAssignmentId() != null) {
            Assignment assignment = assignmentRepository.findById(groupRequestDto.getAssignmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + groupRequestDto.getAssignmentId()));
            group.setAssignments(Collections.singletonList(assignment));
        }

        Group updatedGroup = groupRepository.save(group);
        return mapToResponseDto(updatedGroup);
    }

    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));
        groupRepository.delete(group);
    }

    private GroupResponseDto mapToResponseDto(Group group) {
        GroupResponseDto responseDto = modelMapper.map(group, GroupResponseDto.class);
        responseDto.setSectionName(group.getSection().getName());

        List<String> memberNames = group.getUsers().stream()
                .map(User::getName)
                .collect(Collectors.toList());
        responseDto.setMemberNames(memberNames);

        if (group.getAssignments() != null && !group.getAssignments().isEmpty()) {
            responseDto.setAssignmentTitle(group.getAssignments().get(0).getTitle());
        }

        if (group.getGrades() != null) {
            List<GradeResponseDto> gradeDtos = group.getGrades().stream()
                    .map(grade -> modelMapper.map(grade, GradeResponseDto.class))
                    .collect(Collectors.toList());
            responseDto.setGrades(gradeDtos);
        }

        return responseDto;
    }
}
