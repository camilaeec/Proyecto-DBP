package com.api.rest.canvas2.Users.domain;

import com.api.rest.canvas2.Assignment.infrastructure.AssignmentRepository;
import com.api.rest.canvas2.Course.domain.Course;
import com.api.rest.canvas2.Course.infrastructure.CourseRepository;
import com.api.rest.canvas2.Grades.domain.Grade;
import com.api.rest.canvas2.Grades.dto.GradeResponseDto;
import com.api.rest.canvas2.Grades.infrastructure.GradeRepository;
import com.api.rest.canvas2.Quiz.infrastructure.QuizRepository;
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
    private final GradeRepository gradeRepository;
    private final AssignmentRepository assignmentRepository;
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, GradeRepository gradeRepository,
                       AssignmentRepository assignmentRepository, QuizRepository quizRepository,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.gradeRepository = gradeRepository;
        this.assignmentRepository = assignmentRepository;
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = modelMapper.map(userRequestDto, User.class);
        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
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

    /*
        public List<GradeResponseDto> getGradesByUserAndAssignmentOrQuiz(Long userId, Long assignmentId, Long quizId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Grade> grades;
        if (assignmentId != null) {
            grades = gradeRepository.findByUserIdAndAssignmentId(userId, assignmentId);
        } else if (quizId != null) {
            grades = gradeRepository.findByUserIdAndQuizId(userId, quizId);
        } else {
            throw new IllegalArgumentException("Either assignmentId or quizId must be provided");
        }

        return grades.stream().map(this::mapToGradeDto).collect(Collectors.toList());
    }
     */

    private UserResponseDto mapToResponseDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    private GradeResponseDto mapToGradeDto(Grade grade) {
        return modelMapper.map(grade, GradeResponseDto.class);
    }
}
