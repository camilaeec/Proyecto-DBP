package com.api.rest.canvas2.Grades.domain;

import com.api.rest.canvas2.Assignment.domain.Assignment;
import com.api.rest.canvas2.Assignment.infrastructure.AssignmentRepository;
import com.api.rest.canvas2.Grades.dto.GradeRequestDto;
import com.api.rest.canvas2.Grades.dto.GradeResponseDto;
import com.api.rest.canvas2.Grades.infrastructure.GradeRepository;
import com.api.rest.canvas2.Group.domain.Group;
import com.api.rest.canvas2.Group.infrastructure.GroupRepository;
import com.api.rest.canvas2.Quiz.domain.Quiz;
import com.api.rest.canvas2.Quiz.infrastructure.QuizRepository;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.auth.utils.AuthorizationUtils;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final QuizRepository quizRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;

    public GradeService(GradeRepository gradeRepository, UserRepository userRepository,
                        AssignmentRepository assignmentRepository, QuizRepository quizRepository,
                        GroupRepository groupRepository, ModelMapper modelMapper,
                        AuthorizationUtils authorizationUtils) {
        this.gradeRepository = gradeRepository;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
        this.quizRepository = quizRepository;
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.authorizationUtils = authorizationUtils;
    }

    public GradeResponseDto createGrade(GradeRequestDto gradeRequestDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can create grades.");
        }

        Assignment assignment = assignmentRepository.findById(gradeRequestDto.getAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        Group group = groupRepository.findById(gradeRequestDto.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        for (User user : group.getUsers()) {
            Grade grade = new Grade();
            grade.setUser(user);
            grade.setAssignment(assignment);
            grade.setGroup(group);
            grade.setGrade(gradeRequestDto.getGrade());
            grade.setFeedback(gradeRequestDto.getFeedback());
            gradeRepository.save(grade);
        }

        return new GradeResponseDto();
    }


    public List<GradeResponseDto> getGradesByUserAndAssignmentOrQuiz(Long userId, Long assignmentId, Long quizId) {
        if (!authorizationUtils.isAdminOrResourceOwner(userId)) {
            throw new SecurityException("You don't have permission to view these grades.");
        }

        List<Grade> grades;
        if (assignmentId != null) {
            grades = gradeRepository.findByUserIdAndAssignmentId(userId, assignmentId);
        } else {
            grades = gradeRepository.findByUserIdAndQuizId(userId, quizId);
        }

        return grades.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    public GradeResponseDto getGradeById(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + gradeId));

        if (!authorizationUtils.isAdminOrResourceOwner(grade.getUser().getId())) {
            throw new SecurityException("You don't have permission to view this grade.");
        }

        return mapToResponseDto(grade);
    }

    public GradeResponseDto updateGrade(Long gradeId, GradeRequestDto gradeRequestDto) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found"));

        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can update grades.");
        }

        grade.setGrade(gradeRequestDto.getGrade());
        grade.setFeedback(gradeRequestDto.getFeedback());

        return mapToResponseDto(gradeRepository.save(grade));
    }

    public void deleteGrade(Long gradeId) {
        if (!authorizationUtils.isAdmin()) {
            throw new SecurityException("Only admins can delete grades.");
        }

        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found"));
        gradeRepository.delete(grade);
    }

    private GradeResponseDto mapToResponseDto(Grade grade) {
        GradeResponseDto gradeResponseDto = modelMapper.map(grade, GradeResponseDto.class);
        gradeResponseDto.setStudentName(grade.getUser().getName());
        if (grade.getAssignment() != null) {
            gradeResponseDto.setAssignmentTitle(grade.getAssignment().getTitle());
        }
        if (grade.getQuiz() != null) {
            gradeResponseDto.setQuizTitle(grade.getQuiz().getTitle());
        }
        if (grade.getGroup() != null) {
            gradeResponseDto.setGroupName(grade.getGroup().getName());
        }
        return gradeResponseDto;
    }
}
