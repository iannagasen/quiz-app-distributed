package dev.agasen.microsrv.api.core.quiz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface QuizService {

  @PostMapping("/quiz")
  Quiz submitQuiz(@RequestBody QuizAnswerList answers);

  /*
   * GET /quiz/{quizId}
   */
  @GetMapping("/quiz/{quizId}")
  Quiz getQuizResult(@PathVariable Long quizId);
  
}
