package dev.agasen.microsrv.api.manager;

import dev.agasen.microsrv.api.core.question.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizCompositeItem {
 
  private Long id;
  private Question question;
  private Long selectedChoiceId;
}
