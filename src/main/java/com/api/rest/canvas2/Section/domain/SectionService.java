package com.api.rest.canvas2.Section.domain;

import com.api.rest.canvas2.Assistant.domain.Assistant;
import com.api.rest.canvas2.Assistant.dto.AssistantResponseDto;
import com.api.rest.canvas2.Assistant.infrastructure.AssistantRepository;
import com.api.rest.canvas2.Course.domain.Course;
import com.api.rest.canvas2.Course.infrastructure.CourseRepository;
import com.api.rest.canvas2.Section.dto.SectionRequestDto;
import com.api.rest.canvas2.Section.dto.SectionResponseDto;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.dto.UserResponseDto;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final AssistantRepository assistantRepository;
    private final ModelMapper modelMapper;

    public SectionService(SectionRepository sectionRepository, CourseRepository courseRepository,
                          UserRepository userRepository, AssistantRepository assistantRepository,
                          ModelMapper modelMapper) {
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.assistantRepository = assistantRepository;
        this.modelMapper = modelMapper;
    }

    public SectionResponseDto createSection(Long courseId, SectionRequestDto sectionRequestDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        Section section = modelMapper.map(sectionRequestDto, Section.class);
        section.setCourse(course);
        Section savedSection = sectionRepository.save(section);

        return modelMapper.map(savedSection, SectionResponseDto.class);
    }

    public List<SectionResponseDto> getAllSections(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        List<Section> sections = sectionRepository.findByCourseId(courseId);
        return sections.stream()
                .map(section -> modelMapper.map(section, SectionResponseDto.class))
                .collect(Collectors.toList());
    }

    public SectionResponseDto getSectionById(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + sectionId));
        return modelMapper.map(section, SectionResponseDto.class);
    }

    public SectionResponseDto updateSection(Long sectionId, SectionRequestDto sectionRequestDto) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + sectionId));

        section.setName(sectionRequestDto.getName());
        section.setType(sectionRequestDto.getType());

        Section updatedSection = sectionRepository.save(section);
        return modelMapper.map(updatedSection, SectionResponseDto.class);
    }

    public void deleteSection(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + sectionId));
        sectionRepository.delete(section);
    }

    public SectionResponseDto assignUsersToSection(Long sectionId, List<Long> userIds) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + sectionId));

        List<User> users = userRepository.findAllById(userIds);
        section.setUsers(users);

        Section updatedSection = sectionRepository.save(section);
        return modelMapper.map(updatedSection, SectionResponseDto.class);
    }

    public List<UserResponseDto> getUsersInSection(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + sectionId));

        List<User> users = section.getUsers();
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    public List<AssistantResponseDto> getAssistantsBySection(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + sectionId));

        List<Assistant> assistants = section.getAssistants();
        return assistants.stream()
                .map(assistant -> modelMapper.map(assistant, AssistantResponseDto.class))
                .collect(Collectors.toList());
    }

    public SectionResponseDto assignAssistantsToSection(Long sectionId, List<Long> assistantIds) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + sectionId));

        List<Assistant> assistants = assistantRepository.findAllById(assistantIds);
        section.getAssistants().addAll(assistants);

        Section updatedSection = sectionRepository.save(section);
        return modelMapper.map(updatedSection, SectionResponseDto.class);
    }
}