package com.api.rest.canvas2.Question.domain;

import com.api.rest.canvas2.Answer.domain.Answer;
import com.api.rest.canvas2.Answer.dto.AnswerDto;
import com.api.rest.canvas2.Question.dto.QuestionDto;
import com.api.rest.canvas2.Question.infrastructure.QuestionRepository;
import com.api.rest.canvas2.Quiz.domain.Quiz;
import com.api.rest.canvas2.Quiz.infrastructure.QuizRepository;
import com.api.rest.canvas2.auth.utils.AuthorizationUtils;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;

    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository,
                           ModelMapper modelMapper, AuthorizationUtils authorizationUtils) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
        this.authorizationUtils = authorizationUtils;
    }

    public QuestionDto createQuestion(Long quizId, QuestionDto questionDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can create questions.");
        }

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        Question question = new Question();
        question.setContent(questionDto.getContent());
        question.setIsMultipleChoice(questionDto.getIsMultipleChoice());
        question.setQuiz(quiz);

        Question savedQuestion = questionRepository.save(question);

        List<Answer> answers = questionDto.getAnswers().stream()
                .map(answerDto -> {
                    Answer answer = mapToAnswerEntity(answerDto);
                    answer.setQuestion(savedQuestion);
                    return answer;
                })
                .collect(Collectors.toList());

        savedQuestion.setAnswers(answers);
        questionRepository.save(savedQuestion);

        return mapToQuestionDto(savedQuestion);
    }


    public List<QuestionDto> getQuestionsByQuiz(Long quizId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);
        return questions.stream()
                .map(this::mapToQuestionDto)
                .collect(Collectors.toList());
    }

    public QuestionDto getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
        return mapToQuestionDto(question);
    }

    public QuestionDto updateQuestion(Long questionId, QuestionDto questionDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can update questions.");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));

        question.setContent(questionDto.getContent());
        question.setIsMultipleChoice(questionDto.getIsMultipleChoice());

        List<Answer> answers = questionDto.getAnswers().stream()
                .map(this::mapToAnswerEntity)
                .collect(Collectors.toList());

        question.setAnswers(answers);
        Question updatedQuestion = questionRepository.save(question);

        return mapToQuestionDto(updatedQuestion);
    }

    public void deleteQuestion(Long questionId) {
        if (!authorizationUtils.isAdmin()) {
            throw new SecurityException("Only admins can delete questions.");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
        questionRepository.delete(question);
    }

    private QuestionDto mapToQuestionDto(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }

    private Answer mapToAnswerEntity(AnswerDto answerDto) {
        Answer answer = new Answer();
        answer.setContent(answerDto.getContent());
        answer.setIsCorrect(answerDto.getIsCorrect());
        return answer;
    }
}
