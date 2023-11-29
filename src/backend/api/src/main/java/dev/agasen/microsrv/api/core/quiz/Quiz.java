package dev.agasen.microsrv.api.core.quiz;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

  private Long id;
  private List<QuizItem> questions;
  private LocalDateTime dateTaken;
  private Long timeTaken;

}
