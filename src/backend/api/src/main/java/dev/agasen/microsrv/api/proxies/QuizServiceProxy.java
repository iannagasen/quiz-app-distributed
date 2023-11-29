package dev.agasen.microsrv.api.proxies;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import dev.agasen.microsrv.api.core.quiz.Quiz;
import dev.agasen.microsrv.api.core.quiz.QuizAnswerList;

public interface QuizServiceProxy {

  @PostExchange("/quiz")
  Quiz submitQuiz(@RequestBody QuizAnswerList answers);

  /**
   * GET /quiz/{quizId}
   */
  @GetExchange("/quiz/{quizId}")
  Quiz getQuizResult(@PathVariable("quizId") Long quizId);
  
}
