package dev.agasen.microsrv.api.core.question;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface QuestionService {

  @GetMapping("/question")
  List<Question> getQuestions(@RequestParam(name="topic",required=true) String topic);

  @GetMapping("/question/{questionId}")
  Question getQuestion(@PathVariable("questionId") Long questionId);

  @PostMapping("/question")
  Question createQuestion(@RequestBody Question question);
  
  /**
   * GET /questions/{questionIds}"
   */
  @GetMapping("/questions/{questionIds}")
  List<Question> getQuestions(@PathVariable(name="questionIds") List<Long> questionIds);

  @GetMapping("/question/answers/{questionIds}")
  List<CorrectQuestionAnswer> getQuestionAnswers(@PathVariable(name="questionIds") List<Long> questionIds);

  
}
