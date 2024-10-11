package com.api.rest.canvas2.Course.domain;

import com.api.rest.canvas2.Course.dto.CourseRequestDto;
import com.api.rest.canvas2.Course.dto.CourseResponseDto;
import com.api.rest.canvas2.Course.infrastructure.CourseRepository;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.dto.UserResponseDto;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
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

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) {
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
        return modelMapper.map(course, CourseResponseDto.class);
    }

    public CourseResponseDto getCourseByName(String name) {
        Course course = courseRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with name: " + name));
        return modelMapper.map(course, CourseResponseDto.class);
    }

    public CourseResponseDto updateCourse(Long id, CourseRequestDto courseRequestDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        // Actualizar solo los campos relevantes
        course.setName(courseRequestDto.getName());
        course.setDescription(courseRequestDto.getDescription());

        Course updatedCourse = courseRepository.save(course);
        return modelMapper.map(updatedCourse, CourseResponseDto.class);
    }


    public void deleteCourse(Long id) {
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

}
