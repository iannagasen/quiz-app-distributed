package dev.agasen.microsrv.api.core.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CorrectQuestionAnswer {
  private Long id;
  private Choice correctAnswer;
}
