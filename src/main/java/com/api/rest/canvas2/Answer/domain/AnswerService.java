package com.api.rest.canvas2.Answer.domain;

import com.api.rest.canvas2.Answer.dto.AnswerDto;
import com.api.rest.canvas2.Answer.infrastructure.AnswerRepository;
import com.api.rest.canvas2.Question.domain.Question;
import com.api.rest.canvas2.Question.infrastructure.QuestionRepository;
import com.api.rest.canvas2.auth.utils.AuthorizationUtils;
import com.api.rest.canvas2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository,
                         ModelMapper modelMapper, AuthorizationUtils authorizationUtils) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
        this.authorizationUtils = authorizationUtils;
    }

    public AnswerDto createAnswer(Long questionId, AnswerDto answerDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can create answers.");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));

        Answer answer = new Answer();
        answer.setContent(answerDto.getContent());
        answer.setIsCorrect(answerDto.getIsCorrect());
        answer.setQuestion(question);

        Answer savedAnswer = answerRepository.save(answer);
        return mapToAnswerDto(savedAnswer);
    }

    public List<AnswerDto> getAnswersByQuestion(Long questionId) {
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        return answers.stream()
                .map(this::mapToAnswerDto)
                .collect(Collectors.toList());
    }

    public AnswerDto getAnswerById(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id: " + answerId));
        return mapToAnswerDto(answer);
    }

    public AnswerDto updateAnswer(Long answerId, AnswerDto answerDto) {
        if (!authorizationUtils.isTeacherOrAdmin()) {
            throw new SecurityException("Only teachers or admins can update answers.");
        }

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id: " + answerId));

        answer.setContent(answerDto.getContent());
        answer.setIsCorrect(answerDto.getIsCorrect());

        Answer updatedAnswer = answerRepository.save(answer);
        return mapToAnswerDto(updatedAnswer);
    }

    public void deleteAnswer(Long answerId) {
        if (!authorizationUtils.isAdmin()) {
            throw new SecurityException("Only admins can delete answers.");
        }

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id: " + answerId));
        answerRepository.delete(answer);
    }

    private AnswerDto mapToAnswerDto(Answer answer) {
        return modelMapper.map(answer, AnswerDto.class);
    }
}
