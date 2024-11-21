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
import com.api.rest.canvas2.auth.utils.AuthorizationUtils;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final AuthorizationUtils authorizationUtils;

    public UserService(UserRepository userRepository, GradeRepository gradeRepository,
                       AssignmentRepository assignmentRepository, QuizRepository quizRepository,
                       ModelMapper modelMapper, AuthorizationUtils authorizationUtils) {
        this.userRepository = userRepository;
        this.gradeRepository = gradeRepository;
        this.assignmentRepository = assignmentRepository;
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
        this.authorizationUtils = authorizationUtils;
    }

    /*
        public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = modelMapper.map(userRequestDto, User.class);
        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
    }
     */

    public UserResponseDto getUserById(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new SecurityException("You don't have permission to view this user.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return mapToResponseDto(user);
    }

    public UserResponseDto getMe() {
        String email = authorizationUtils.getCurrentUserEmail(); // Obtenemos el email del usuario autenticado
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToResponseDto(user); // Mapear el usuario a DTO
    }


    public UserResponseDto getUserByName(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with name: " + name));
        return mapToResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        if (!authorizationUtils.isAdminOrResourceOwner(null) && !authorizationUtils.isTeacher()) {
            throw new SecurityException("You don't have permission to view all users.");
        }

        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new SecurityException("You don't have permission to delete this user.");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new SecurityException("You don't have permission to update this user.");
        }

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

    public UserResponseDto updateUserRole(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setRole(Role.valueOf(role.toUpperCase()));
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return (UserDetails) user;
        };
    }

    public User findByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user;
    }
}
