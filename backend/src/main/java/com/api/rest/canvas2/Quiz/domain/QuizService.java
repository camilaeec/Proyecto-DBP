package com.api.rest.canvas2.Quiz.domain;

import com.api.rest.canvas2.Answer.domain.Answer;
import com.api.rest.canvas2.Answer.dto.AnswerDto;
import com.api.rest.canvas2.Grades.domain.Grade;
import com.api.rest.canvas2.Grades.dto.GradeResponseDto;
import com.api.rest.canvas2.Grades.infrastructure.GradeRepository;
import com.api.rest.canvas2.Question.domain.Question;
import com.api.rest.canvas2.Question.dto.QuestionDto;
import com.api.rest.canvas2.Quiz.dto.QuizAnswerDto;
import com.api.rest.canvas2.Quiz.dto.QuizRequestDto;
import com.api.rest.canvas2.Quiz.dto.QuizResponseDto;
import com.api.rest.canvas2.Quiz.infrastructure.QuizRepository;
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
public class QuizService {

    private final QuizRepository quizRepository;
    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final AuthorizationUtils authorizationUtils;

    public QuizService(QuizRepository quizRepository, SectionRepository sectionRepository, ModelMapper modelMapper, UserRepository userRepository, GradeRepository gradeRepository, AuthorizationUtils authorizationUtils) {
        this.quizRepository = quizRepository;
        this.sectionRepository = sectionRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.gradeRepository = gradeRepository;
        this.authorizationUtils = authorizationUtils;
    }

    public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can create quizzes.");
        }

        Section section = sectionRepository.findById(quizRequestDto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + quizRequestDto.getSectionId()));

        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequestDto.getTitle());
        quiz.setDescription(quizRequestDto.getDescription());
        quiz.setDueDate(quizRequestDto.getDueDate());
        quiz.setDuration(quizRequestDto.getDuration());
        quiz.setSection(section);

        Quiz savedQuiz = quizRepository.save(quiz);
        return mapToQuizResponseDto(savedQuiz);
    }




    public List<QuizResponseDto> getQuizzesBySection(Long sectionId) {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .filter(quiz -> quiz.getSection().getId().equals(sectionId))
                .map(this::mapToQuizResponseDto)
                .collect(Collectors.toList());
    }

    public QuizResponseDto getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
        return mapToQuizResponseDto(quiz);
    }

    public QuizResponseDto updateQuiz(Long id, QuizRequestDto quizRequestDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can update quizzes.");
        }

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

        Section section = sectionRepository.findById(quizRequestDto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + quizRequestDto.getSectionId()));

        quiz.setTitle(quizRequestDto.getTitle());
        quiz.setDescription(quizRequestDto.getDescription());
        quiz.setDueDate(quizRequestDto.getDueDate());
        quiz.setDuration(quizRequestDto.getDuration());
        quiz.setSection(section);

        List<Question> questions = quizRequestDto.getQuestions().stream()
                .map(this::mapToQuestionEntity)
                .collect(Collectors.toList());

        quiz.setQuestions(questions);
        Quiz updatedQuiz = quizRepository.save(quiz);
        return mapToQuizResponseDto(updatedQuiz);
    }


    public void deleteQuiz(Long id) {
        if (!authorizationUtils.isAdmin()) {
            throw new SecurityException("Only admins can delete quizzes.");
        }

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
        quizRepository.delete(quiz);
    }

    public GradeResponseDto gradeQuiz(Long quizId, Long userId, List<QuizAnswerDto> userAnswers) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        int totalScore = 0;
        for (Question question : quiz.getQuestions()) {
            QuizAnswerDto userAnswer = userAnswers.stream()
                    .filter(a -> a.getQuestionId().equals(question.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Answer not found for question id: " + question.getId()));

            if (question.getIsMultipleChoice()) {
                Answer correctAnswer = question.getAnswers().stream()
                        .filter(Answer::getIsCorrect)
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("No correct answer found for question"));
                if (correctAnswer.getContent().equals(userAnswer.getAnswer())) {
                    totalScore += question.getPoints();
                }
            } else {
                Answer correctAnswer = question.getAnswers().get(0);
                if (correctAnswer.getContent().equalsIgnoreCase(userAnswer.getAnswer())) {
                    totalScore += question.getPoints();
                }
            }
        }

        Grade grade = new Grade();
        grade.setGrade(totalScore);
        grade.setQuiz(quiz);
        grade.setUser(user);
        gradeRepository.save(grade);

        return mapToResponseDto(grade);
    }


    private Question mapToQuestionEntity(QuestionDto questionDto) {
        Question question = new Question();
        question.setContent(questionDto.getContent());
        question.setIsMultipleChoice(questionDto.getIsMultipleChoice());
        List<Answer> answers = questionDto.getAnswers().stream()
                .map(this::mapToAnswerEntity)
                .collect(Collectors.toList());
        question.setAnswers(answers);
        return question;
    }

    private Answer mapToAnswerEntity(AnswerDto answerDto) {
        Answer answer = new Answer();
        answer.setContent(answerDto.getContent());
        answer.setIsCorrect(answerDto.getIsCorrect());
        return answer;
    }

    private QuizResponseDto mapToQuizResponseDto(Quiz quiz) {
        QuizResponseDto quizResponseDto = modelMapper.map(quiz, QuizResponseDto.class);
        quizResponseDto.setSectionName(quiz.getSection().getType());
        return quizResponseDto;
    }

    private GradeResponseDto mapToResponseDto(Grade grade) {
        return modelMapper.map(grade, GradeResponseDto.class);
    }
}
