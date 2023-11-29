package dev.agasen.microsrv.api.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import dev.agasen.microsrv.api.core.question.Question;
import dev.agasen.microsrv.api.core.quiz.Quiz;
import dev.agasen.microsrv.api.core.quiz.QuizAnswerList;

public interface QuizManagerService {

  /**
   * WORKFLOW:
   *  1. call the question service to check the answers
   *  2. save the quiz result (quiz-service) 
   *      - can we make this asynchronous to step 1)
   *  3. generate event for saving analytics
   */
  @PostMapping("/manager/quiz")
  Quiz submitQuiz(@RequestBody QuizAnswerList submittedQuiz);


  /**
   * WORKFLOW:
   *  1. Create the logic for generating a quiz 
   *  2. Call the question service
   */
  @GetMapping("/manager/quiz")
  List<Question> generateQuiz (
      @RequestParam Long totalItems,
      @RequestParam Optional<String> topic
  );


  /**
   * WORKFLOW
   *  1. Get the Quiz from quiz-service
   *  2. Get each Questions from Quiz and aggregate
   *  3. Get the analytics, async from 2
   */
  @GetMapping("/manager/quiz/{quizId}")
  Quiz getQuizResult(@PathVariable Long quizId);


}
