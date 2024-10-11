package com.api.rest.canvas2.Quiz.domain;

import com.api.rest.canvas2.Answer.domain.Answer;
import com.api.rest.canvas2.Answer.dto.AnswerDto;
import com.api.rest.canvas2.Question.domain.Question;
import com.api.rest.canvas2.Question.dto.QuestionDto;
import com.api.rest.canvas2.Quiz.dto.QuizRequestDto;
import com.api.rest.canvas2.Quiz.dto.QuizResponseDto;
import com.api.rest.canvas2.Quiz.infrastructure.QuizRepository;
import com.api.rest.canvas2.Section.domain.Section;
import com.api.rest.canvas2.Section.infrastructure.SectionRepository;
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

    public QuizService(QuizRepository quizRepository, SectionRepository sectionRepository, ModelMapper modelMapper) {
        this.quizRepository = quizRepository;
        this.sectionRepository = sectionRepository;
        this.modelMapper = modelMapper;
    }

    public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
        Section section = sectionRepository.findById(quizRequestDto.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + quizRequestDto.getSectionId()));

        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequestDto.getTitle());
        quiz.setDescription(quizRequestDto.getDescription());
        quiz.setDueDate(quizRequestDto.getDueDate());
        quiz.setDuration(quizRequestDto.getDuration());
        quiz.setSection(section);

        List<Question> questions = quizRequestDto.getQuestions().stream()
                .map(this::mapToQuestionEntity)
                .collect(Collectors.toList());

        quiz.setQuestions(questions);
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
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
        quizRepository.delete(quiz);
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
}
