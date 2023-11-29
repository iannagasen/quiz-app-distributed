package dev.agasen.microsrv.manager.quiz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import dev.agasen.microsrv.api.core.question.Question;
import dev.agasen.microsrv.api.core.quiz.Quiz;
import dev.agasen.microsrv.api.core.quiz.QuizAnswerList;
import dev.agasen.microsrv.api.manager.QuizManagerService;
import dev.agasen.microsrv.api.proxies.QuestionServiceProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QuizManagerServiceImpl implements QuizManagerService {

  private final QuestionServiceProxy questionServiceProxy;

  @Override
  public Quiz submitQuiz(QuizAnswerList submittedQuiz) {
    throw new UnsupportedOperationException("Unimplemented method 'submitQuiz'");
  }

  @Override
  public List<Question> generateQuiz(Long totalItems, Optional<String> topic) {
    log.info("QuizManagerServiceImpl::generateQuiz");
    return questionServiceProxy.getQuestions(topic.get());
  }

  @Override
  public Quiz getQuizResult(Long quizId) {
    throw new UnsupportedOperationException("Unimplemented method 'getQuizResult'");
  }
  
}
