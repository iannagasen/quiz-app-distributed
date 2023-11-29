package dev.agasen.microsrv.api.core.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizItemAnswer {
  
  private Long questionId;
  private Long choiceId;

}
