package dev.agasen.microsrv.api.proxies;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import dev.agasen.microsrv.api.core.question.Question;

public interface QuestionServiceProxy {

  @GetExchange("/question")
  List<Question> getQuestions(@RequestParam(name="topic",required=true) String topic);

  @GetExchange("/question/{questionId}")
  Question getQuestion(@PathVariable("questionId") Long questionId);

  @PostExchange("/question")
  Question createQuestion(@RequestBody Question question);

  @GetExchange("/questions/{questionIds}")
  List<Question> getQuestionsByMultipleIds(@PathVariable("questionIds") String questionIds);

}
