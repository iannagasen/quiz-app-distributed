package dev.agasen.microsrv.core.question.service;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import dev.agasen.microsrv.api.core.question.Choice;
import dev.agasen.microsrv.api.core.question.CorrectQuestionAnswer;
import dev.agasen.microsrv.api.core.question.NoCorrectAnswerException;
import dev.agasen.microsrv.api.core.question.Question;
import dev.agasen.microsrv.api.core.question.QuestionService;
import dev.agasen.microsrv.api.exceptions.NotFoundException;
import dev.agasen.microsrv.core.question.persistence.ChoiceEntity;
import dev.agasen.microsrv.core.question.persistence.QuestionEntity;
import dev.agasen.microsrv.core.question.persistence.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {

  private final QuestionMapper questionMapper;
  private final ChoiceMapper choiceMapper;
  private final QuestionRepository questionRepository;
  
  
  @Override
  @Transactional
  public List<Question> getQuestions(String topic) {
  
    log.info("QuestionServiceImpl::getQuestions - topic: {}", topic);
  
    List<QuestionEntity> questions = questionRepository.findAllByTopic(topic);

    return questionMapper.entityListToApiList(questions);

  }

  
  @Override
  @Transactional
  public Question getQuestion(Long questionId) {

    log.info("QuestionServiceImpl::getQuestion - questionId: {}", questionId);

    return questionRepository.findById(questionId)
        .map(questionMapper::entityToApi)
        .orElseThrow(() -> new NotFoundException("Product Id not found with ID: " + questionId));
  }


  @Override
  @Transactional
  public Question createQuestion(Question question) {

    log.info("QuestionServiceImpl::createQuestion with id: {}", question.getId());

    // validate
    boolean hasNoCorrectAnswer = question.getChoices().stream()
        .map(Choice::isCorrect)
        .allMatch(Boolean.FALSE::equals);

    if (hasNoCorrectAnswer) throw NoCorrectAnswerException.withQuestion(question);

    QuestionEntity entity = questionMapper.apiToEntity(question);
    QuestionEntity savedEntity = questionRepository.save(entity);

    return questionMapper.entityToApi(savedEntity);
    // return question;
  }


  @Override
  @Transactional
  public List<Question> getQuestions(List<Long> questionIds) {

    log.info("QuestionServiceImpl::getQuestions with multiple ids");

    List<QuestionEntity> entities = questionRepository.findByIdIn(questionIds);

    return questionMapper.entityListToApiList(entities);
  }


  @Override
  @Transactional
  public List<CorrectQuestionAnswer> getQuestionAnswers(List<Long> questionIds) {
    log.info("QuestionServiceImpl::getQuestionAnswers");

    return questionIds.stream()
        .map(id -> {
            var correctAnswer = questionRepository
                .findById(id)
                .flatMap(question -> question.getChoices().stream().filter(ChoiceEntity::isCorrect).findFirst())
                .map(choiceMapper::entityToApi)
                .orElse(null);
            return new CorrectQuestionAnswer(id, correctAnswer);
        })
        .toList();  
  }
  
}
