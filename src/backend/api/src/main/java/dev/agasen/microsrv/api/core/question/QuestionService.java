package dev.agasen.microsrv.api.core.question;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface QuestionService {

  @GetMapping("/question")
  List<Question> getQuestions(@RequestParam(required=true) String topic);

  @GetMapping("/question/{questionId}")
  Question getQuestion(@PathVariable Long questionId);

  @PostMapping("/question")
  Question createQuestion(@RequestBody Question question);
  
}
