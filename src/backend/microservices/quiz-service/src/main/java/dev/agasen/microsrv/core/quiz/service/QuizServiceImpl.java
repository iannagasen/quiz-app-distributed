package dev.agasen.microsrv.core.quiz.service;

import org.springframework.web.bind.annotation.RestController;

import dev.agasen.microsrv.api.core.quiz.Quiz;
import dev.agasen.microsrv.api.core.quiz.QuizAnswerList;
import dev.agasen.microsrv.api.core.quiz.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

  

  @Override
  public Quiz submitQuiz(QuizAnswerList answers) {
    log.info("QuizServiceImpl::submitQuiz");
    throw new UnsupportedOperationException("Unimplemented method 'submitQuiz'");
  }



  @Override
  public Quiz getQuizResult(Long quizId) {
    log.info("QuizServiceImpl::getQuizResult");
    throw new UnsupportedOperationException("Unimplemented method 'getQuizResult'");
  }
  
}
