package dev.agasen.microsrv.api.core.quiz;

import dev.agasen.microsrv.api.core.question.Choice;
import dev.agasen.microsrv.api.core.question.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizItem {

  private Long id;
  private Question question;
  private Choice selectedChoice;

}
