package dev.agasen.microsrv.api.core.question;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
  private Long id;
  private String topic;
  private String question;
  private Set<Choice> choices;
}
