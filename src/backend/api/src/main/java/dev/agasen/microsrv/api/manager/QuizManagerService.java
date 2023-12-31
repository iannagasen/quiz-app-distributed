package dev.agasen.microsrv.api.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import dev.agasen.microsrv.api.core.question.Question;
import dev.agasen.microsrv.api.core.quiz.QuizAnswerList;
import reactor.core.publisher.Mono;

public interface QuizManagerService {

  @PostMapping("/manager/quiz")
  QuizComposite submitQuiz(@RequestBody QuizAnswerList submittedQuiz);


  @GetMapping("/manager/quiz")
  List<Question> generateQuiz (
    @RequestParam Long totalItems,
    @RequestParam Optional<String> topic
  );


  @GetMapping("/manager/quiz/{quizId}")
  QuizComposite getQuizResult(@PathVariable long quizId);


  @PostMapping("/manager/quiz/question")
  Mono<Void> createQuestion(@RequestBody Question question);

  
  @GetMapping("/question")
  List<Question> getQuestions(@RequestParam(name="topic",required=true) String topic);
  


}
