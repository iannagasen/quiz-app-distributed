package dev.agasen.microsrv.api.core.quiz;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswerList {
  
  private List<QuizItemAnswer> questionAnswers;
  
}
