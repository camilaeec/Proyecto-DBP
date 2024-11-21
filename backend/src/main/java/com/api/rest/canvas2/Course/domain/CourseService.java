package com.api.rest.canvas2.Course.domain;

import com.api.rest.canvas2.Course.dto.CourseRequestDto;
import com.api.rest.canvas2.Course.dto.CourseResponseDto;
import com.api.rest.canvas2.Course.infrastructure.CourseRepository;
import com.api.rest.canvas2.Section.dto.SectionDto;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.dto.UserResponseDto;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.auth.utils.AuthorizationUtils;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository,
                         ModelMapper modelMapper, AuthorizationUtils authorizationUtils) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.authorizationUtils = authorizationUtils;
    }

    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) {
        if (!authorizationUtils.isAdmin()) {
            throw new SecurityException("Only Admins can create courses.");
        }

        Course course = modelMapper.map(courseRequestDto, Course.class);
        Course savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseResponseDto.class);
    }


    public List<CourseResponseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> modelMapper.map(course, CourseResponseDto.class))
                .collect(Collectors.toList());
    }

    public CourseResponseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return mapCourseToDto(course);
    }

    public CourseResponseDto getCourseByName(String name) {
        Course course = courseRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with name: " + name));
        return mapCourseToDto(course);
    }

    public CourseResponseDto updateCourse(Long id, CourseRequestDto courseRequestDto) {
        if (!authorizationUtils.isAdmin()) {
            throw new SecurityException("Only teachers or admins can update courses.");
        }

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        course.setName(courseRequestDto.getName());
        course.setDescription(courseRequestDto.getDescription());

        return modelMapper.map(courseRepository.save(course), CourseResponseDto.class);
    }

    public void deleteCourse(Long id) {
        if (!authorizationUtils.isAdmin()) {
            throw new SecurityException("Only admins can delete courses.");
        }

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        courseRepository.delete(course);
    }

    public List<UserResponseDto> getUsersInCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        List<User> users = course.getSections().stream()
                .flatMap(section -> section.getUsers().stream())
                .distinct()
                .collect(Collectors.toList());

        return users.stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    private CourseResponseDto mapCourseToDto(Course course) {
        CourseResponseDto courseResponseDto = modelMapper.map(course, CourseResponseDto.class);

        List<SectionDto> sectionDtos = course.getSections().stream()
                .map(section -> {
                    SectionDto sectionDto = modelMapper.map(section, SectionDto.class);

                    List<UserResponseDto> userDtos = section.getUsers().stream()
                            .map(user -> modelMapper.map(user, UserResponseDto.class))
                            .collect(Collectors.toList());

                    sectionDto.setUsers(userDtos);

                    return sectionDto;
                })
                .collect(Collectors.toList());

        courseResponseDto.setSections(sectionDtos);

        return courseResponseDto;
    }
}
