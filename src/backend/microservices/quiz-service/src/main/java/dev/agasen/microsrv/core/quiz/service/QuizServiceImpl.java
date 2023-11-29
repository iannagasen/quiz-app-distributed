package dev.agasen.microsrv.core.quiz.service;

import org.springframework.web.bind.annotation.RestController;

import dev.agasen.microsrv.api.core.quiz.Quiz;
import dev.agasen.microsrv.api.core.quiz.QuizAnswerList;
import dev.agasen.microsrv.api.core.quiz.QuizService;
import dev.agasen.microsrv.api.exceptions.NotFoundException;
import dev.agasen.microsrv.core.quiz.persistence.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Transactional
public class QuizServiceImpl implements QuizService {

  private final QuizRepository quizRepository;
  private final QuizMapper quizMapper;

  @Override
  public Quiz submitQuiz(QuizAnswerList answers) {
    log.info("QuizServiceImpl::submitQuiz");
    throw new UnsupportedOperationException("Unimplemented method 'submitQuiz'");
  }

  @Override
  public Quiz getQuizResult(Long quizId) {
    log.info("QuizServiceImpl::getQuizResult");
    return quizRepository
        .findById(quizId)
        .map(quizMapper::quizEntityToApi)
        .orElseThrow(() -> new NotFoundException("Quiz not found with id: " + quizId));
  }
  
}
