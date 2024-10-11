package com.api.rest.canvas2.Question.domain;

import com.api.rest.canvas2.Answer.domain.Answer;
import com.api.rest.canvas2.Answer.dto.AnswerDto;
import com.api.rest.canvas2.Question.dto.QuestionDto;
import com.api.rest.canvas2.Question.infrastructure.QuestionRepository;
import com.api.rest.canvas2.Quiz.domain.Quiz;
import com.api.rest.canvas2.Quiz.infrastructure.QuizRepository;
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

    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
    }


    public QuestionDto createQuestion(Long quizId, QuestionDto questionDto) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        Question question = new Question();
        question.setContent(questionDto.getContent());
        question.setIsMultipleChoice(questionDto.getIsMultipleChoice());
        question.setQuiz(quiz);

        List<Answer> answers = questionDto.getAnswers().stream()
                .map(this::mapToAnswerEntity)
                .collect(Collectors.toList());
        question.setAnswers(answers);

        Question savedQuestion = questionRepository.save(question);
        return mapToQuestionDto(savedQuestion);
    }

    // Obtener todas las preguntas de un quiz
    public List<QuestionDto> getQuestionsByQuiz(Long quizId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);
        return questions.stream()
                .map(this::mapToQuestionDto)
                .collect(Collectors.toList());
    }

    // Obtener una pregunta por ID
    public QuestionDto getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
        return mapToQuestionDto(question);
    }

    public QuestionDto updateQuestion(Long questionId, QuestionDto questionDto) {
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
