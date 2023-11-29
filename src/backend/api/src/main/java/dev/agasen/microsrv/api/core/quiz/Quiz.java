package dev.agasen.microsrv.api.core.quiz;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
  // private LocalDateTime dateTaken;
  // private Long timeTaken;

  @JsonIgnore
  public List<Long> getQuestionIds(){
    return questions.stream().map(QuizItem::getQuestionId).toList();
  }

  @JsonIgnore
  public String getCommaSeparatedIds() {
    return questions.stream()
        .map(QuizItem::getQuestionId)
        .map(String::valueOf)
        .collect(Collectors.joining(","));
  }

}
