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

    public GradeService(GradeRepository gradeRepository, UserRepository userRepository,
                        AssignmentRepository assignmentRepository, QuizRepository quizRepository,
                        GroupRepository groupRepository, ModelMapper modelMapper) {
        this.gradeRepository = gradeRepository;
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
        this.quizRepository = quizRepository;
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
    }

    public GradeResponseDto createGrade(GradeRequestDto gradeRequestDto) {
        User user = userRepository.findById(gradeRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + gradeRequestDto.getUserId()));

        Assignment assignment = null;
        if (gradeRequestDto.getAssignmentId() != null) {
            assignment = assignmentRepository.findById(gradeRequestDto.getAssignmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + gradeRequestDto.getAssignmentId()));
        }

        Quiz quiz = null;
        if (gradeRequestDto.getQuizId() != null) {
            quiz = quizRepository.findById(gradeRequestDto.getQuizId())
                    .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + gradeRequestDto.getQuizId()));
        }

        Group group = null;
        if (gradeRequestDto.getGroupId() != null) {
            group = groupRepository.findById(gradeRequestDto.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + gradeRequestDto.getGroupId()));
        }

        Grade grade = new Grade();
        grade.setGrade(gradeRequestDto.getGrade());
        grade.setFeedback(gradeRequestDto.getFeedback());
        grade.setUser(user);
        grade.setAssignment(assignment);
        grade.setQuiz(quiz);
        grade.setGroup(group);

        Grade savedGrade = gradeRepository.save(grade);
        return mapToResponseDto(savedGrade);
    }

    public List<GradeResponseDto> getGradesByUserAndAssignmentOrQuiz(Long userId, Long assignmentId, Long quizId) {
        List<Grade> grades;
        if (assignmentId != null) {
            grades = gradeRepository.findByUserIdAndAssignmentId(userId, assignmentId);
        } else {
            grades = gradeRepository.findByUserIdAndQuizId(userId, quizId);
        }

        return grades.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public GradeResponseDto getGradeById(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + gradeId));
        return mapToResponseDto(grade);
    }

    public GradeResponseDto updateGrade(Long gradeId, GradeRequestDto gradeRequestDto) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + gradeId));

        grade.setGrade(gradeRequestDto.getGrade());
        grade.setFeedback(gradeRequestDto.getFeedback());

        Grade updatedGrade = gradeRepository.save(grade);
        return mapToResponseDto(updatedGrade);
    }

    public void deleteGrade(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + gradeId));
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
