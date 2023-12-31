package dev.agasen.microsrv.api.core.quiz;

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
  private Long questionId;
  private Long selectedChoiceId;

}
