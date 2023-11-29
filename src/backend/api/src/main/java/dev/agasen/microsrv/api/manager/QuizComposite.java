package dev.agasen.microsrv.api.manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizComposite {
  
  private Long id;
  private List<QuizCompositeItem> questions;
  private LocalDateTime dateTaken;
  private Duration timeTakenn;
}
