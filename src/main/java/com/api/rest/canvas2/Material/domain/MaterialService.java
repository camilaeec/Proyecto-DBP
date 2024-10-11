package com.api.rest.canvas2.Material.domain;

import com.api.rest.canvas2.Material.dto.MaterialRequestDto;
import com.api.rest.canvas2.Material.dto.MaterialResponseDto;
import com.api.rest.canvas2.Material.infrastructure.MaterialRepository;
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
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;

    public MaterialService(MaterialRepository materialRepository, UserRepository userRepository,
                           SectionRepository sectionRepository, ModelMapper modelMapper) {
        this.materialRepository = materialRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.modelMapper = modelMapper;
    }

    public MaterialResponseDto createMaterial(MaterialRequestDto materialRequestDto) {
        User user = userRepository.findById(materialRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + materialRequestDto.getUserId()));

        Section section = sectionRepository.findById(materialRequestDto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + materialRequestDto.getSectionId()));

        Material material = new Material();
        material.setTitle(materialRequestDto.getTitle());
        material.setDescription(materialRequestDto.getDescription());
        material.setFileUrl(materialRequestDto.getFileUrl());
        material.setUser(user);
        material.setSection(section);

        Material savedMaterial = materialRepository.save(material);
        return mapToResponseDto(savedMaterial);
    }

    public List<MaterialResponseDto> getMaterialsBySection(Long sectionId) {
        List<Material> materials = materialRepository.findBySectionId(sectionId);
        return materials.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    public List<MaterialResponseDto> getMaterialsByUser(Long userId) {
        List<Material> materials = materialRepository.findByUserId(userId);
        return materials.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    public MaterialResponseDto updateMaterial(Long id, MaterialRequestDto materialRequestDto) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found with id: " + id));

        material.setTitle(materialRequestDto.getTitle());
        material.setDescription(materialRequestDto.getDescription());
        material.setFileUrl(materialRequestDto.getFileUrl());

        Material updatedMaterial = materialRepository.save(material);
        return mapToResponseDto(updatedMaterial);
    }

    public void deleteMaterial(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found with id: " + id));
        materialRepository.delete(material);
    }

    private MaterialResponseDto mapToResponseDto(Material material) {
        MaterialResponseDto materialResponseDto = modelMapper.map(material, MaterialResponseDto.class);
        materialResponseDto.setUserName(material.getUser().getName());
        materialResponseDto.setSectionName(material.getSection().getType());
        return materialResponseDto;
    }
}
