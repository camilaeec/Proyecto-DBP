package com.api.rest.canvas2.Users.domain;

import com.api.rest.canvas2.Course.domain.Course;
import com.api.rest.canvas2.Course.infrastructure.CourseRepository;
import com.api.rest.canvas2.Grades.domain.Grades;
import com.api.rest.canvas2.Users.dto.UserRequestDto;
import com.api.rest.canvas2.Users.dto.UserResponseDto;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToResponseDto(user);
    }

    public UserResponseDto getUserByName(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with name: " + name));
        return mapToResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));


        user.setName(userRequestDto.getName());
        user.setLastname(userRequestDto.getLastname());
        user.setEmail(userRequestDto.getEmail());
        user.setProfilePicture(userRequestDto.getProfilePicture());

        User updatedUser = userRepository.save(user);
        return mapToResponseDto(updatedUser);
    }


    private UserResponseDto mapToResponseDto(User user) {
        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
        if (user.getCourses() != null) {
            List<String> courseNames = user.getCourses().stream()
                    .map(Course::getName)
                    .collect(Collectors.toList());
            userResponseDto.setCourses(courseNames);
        }else {
            userResponseDto.setCourses(Collections.emptyList());
        }
        return userResponseDto;
    }

    public List<GradeResponseDto> getGradesByUserAndCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        // Filtrar las calificaciones del usuario que pertenecen a ese curso
        List<Grades> grades = user.getGrades().stream()
                .filter(grade -> grade.getCourse().getId().equals(courseId))
                .collect(Collectors.toList());

        return grades.stream().map(this::mapToGradeDto).collect(Collectors.toList());
    }

    private GradeResponseDto mapToGradeDto(Grades grade) {
        return modelMapper.map(grade, GradeResponseDto.class);
    }
}
