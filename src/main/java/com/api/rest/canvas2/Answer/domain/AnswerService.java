package com.api.rest.canvas2.Answer.domain;

import com.api.rest.canvas2.Answer.dto.AnswerDto;
import com.api.rest.canvas2.Answer.infrastructure.AnswerRepository;
import com.api.rest.canvas2.Question.domain.Question;
import com.api.rest.canvas2.Question.infrastructure.QuestionRepository;
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

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    public AnswerDto createAnswer(Long questionId, AnswerDto answerDto) {
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
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id: " + answerId));

        answer.setContent(answerDto.getContent());
        answer.setIsCorrect(answerDto.getIsCorrect());

        Answer updatedAnswer = answerRepository.save(answer);
        return mapToAnswerDto(updatedAnswer);
    }

    public void deleteAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id: " + answerId));
        answerRepository.delete(answer);
    }

    private AnswerDto mapToAnswerDto(Answer answer) {
        return modelMapper.map(answer, AnswerDto.class);
    }
}
