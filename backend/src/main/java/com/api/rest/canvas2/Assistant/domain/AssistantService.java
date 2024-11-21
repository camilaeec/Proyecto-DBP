package com.api.rest.canvas2.Assistant.domain;

import com.api.rest.canvas2.Assistant.dto.AssistantRequestDto;
import com.api.rest.canvas2.Assistant.dto.AssistantResponseDto;
import com.api.rest.canvas2.Assistant.infrastructure.AssistantRepository;
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
public class AssistantService {

    private final AssistantRepository assistantRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;

    public AssistantService(AssistantRepository assistantRepository, UserRepository userRepository,
                            SectionRepository sectionRepository, ModelMapper modelMapper,
                            AuthorizationUtils authorizationUtils) {
        this.assistantRepository = assistantRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.modelMapper = modelMapper;
        this.authorizationUtils = authorizationUtils;
    }

    public AssistantResponseDto createAssistant(AssistantRequestDto assistantRequestDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only admins or teachers can create assistants.");
        }

        User user = userRepository.findById(assistantRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + assistantRequestDto.getUserId()));

        List<Section> sections = sectionRepository.findAllById(assistantRequestDto.getSectionIds());

        Assistant assistant = new Assistant();
        assistant.setUser(user);
        assistant.setIsExternal(assistantRequestDto.getIsExternal());
        assistant.setSections(sections);

        return mapToResponseDto(assistantRepository.save(assistant));
    }

    public List<AssistantResponseDto> getAllAssistants() {
        List<Assistant> assistants = assistantRepository.findAll();
        return assistants.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    public AssistantResponseDto getAssistantById(Long id) {
        Assistant assistant = assistantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assistant not found with id: " + id));
        return mapToResponseDto(assistant);
    }

    public AssistantResponseDto updateAssistant(Long id, AssistantRequestDto assistantRequestDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only admins or teachers can update assistants.");
        }

        Assistant assistant = assistantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assistant not found with id: " + id));

        User user = userRepository.findById(assistantRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + assistantRequestDto.getUserId()));

        List<Section> sections = sectionRepository.findAllById(assistantRequestDto.getSectionIds());

        assistant.setUser(user);
        assistant.setIsExternal(assistantRequestDto.getIsExternal());
        assistant.setSections(sections);

        return mapToResponseDto(assistantRepository.save(assistant));
    }

    public void deleteAssistant(Long id) {
        if (!authorizationUtils.isAdmin()) {
            throw new SecurityException("Only admins can delete assistants.");
        }

        Assistant assistant = assistantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assistant not found with id: " + id));
        assistantRepository.delete(assistant);
    }

    private AssistantResponseDto mapToResponseDto(Assistant assistant) {
        AssistantResponseDto assistantResponseDto = modelMapper.map(assistant, AssistantResponseDto.class);
        assistantResponseDto.setUserName(assistant.getUser().getName());
        List<String> sectionNames = assistant.getSections().stream()
                .map(Section::getType)
                .collect(Collectors.toList());
        assistantResponseDto.setSectionNames(sectionNames);
        return assistantResponseDto;
    }
}
