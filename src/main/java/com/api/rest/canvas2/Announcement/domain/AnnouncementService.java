package com.api.rest.canvas2.Announcement.domain;

import com.api.rest.canvas2.Announcement.dto.AnnouncementRequestDto;
import com.api.rest.canvas2.Announcement.dto.AnnouncementResponseDto;
import com.api.rest.canvas2.Announcement.infrastructure.AnnouncementRepository;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               UserRepository userRepository, SectionRepository sectionRepository,
                               ModelMapper modelMapper) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.modelMapper = modelMapper;
    }

    public AnnouncementResponseDto createAnnouncement(AnnouncementRequestDto announcementRequestDto) {
        User user = userRepository.findById(announcementRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + announcementRequestDto.getUserId()));

        Section section = sectionRepository.findById(announcementRequestDto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + announcementRequestDto.getSectionId()));

        Announcement announcement = new Announcement();
        announcement.setTitle(announcementRequestDto.getTitle());
        announcement.setContent(announcementRequestDto.getContent());
        announcement.setDatePosted(LocalDateTime.now());
        announcement.setUser(user);
        announcement.setSection(section);

        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return mapToResponseDto(savedAnnouncement);
    }

    public List<AnnouncementResponseDto> getAnnouncementsBySection(Long sectionId) {
        List<Announcement> announcements = announcementRepository.findBySectionId(sectionId);
        return announcements.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public AnnouncementResponseDto getAnnouncementById(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + announcementId));
        return mapToResponseDto(announcement);
    }

    public AnnouncementResponseDto updateAnnouncement(Long announcementId, AnnouncementRequestDto announcementRequestDto) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + announcementId));

        announcement.setTitle(announcementRequestDto.getTitle());
        announcement.setContent(announcementRequestDto.getContent());

        Announcement updatedAnnouncement = announcementRepository.save(announcement);
        return mapToResponseDto(updatedAnnouncement);
    }

    public void deleteAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + announcementId));
        announcementRepository.delete(announcement);
    }

    private AnnouncementResponseDto mapToResponseDto(Announcement announcement) {
        AnnouncementResponseDto responseDto = modelMapper.map(announcement, AnnouncementResponseDto.class);
        responseDto.setUserName(announcement.getUser().getName());
        responseDto.setSectionName(announcement.getSection().getName());
        return responseDto;
    }
}

