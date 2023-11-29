package dev.agasen.microsrv.core.quiz.persistence;



import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
@Entity(name="quiz")
public class QuizEntity {
  
  private Long id;
  private List<QuizItemEntity> quizItems;
}
