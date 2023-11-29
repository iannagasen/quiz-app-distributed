package dev.agasen.microsrv.core.quiz.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table
@Entity(name="quiz_item")
public class QuizItemEntity {
  
  private Long id;
  private Long quizId;
  private Long selectedChoiceId;
  
}
