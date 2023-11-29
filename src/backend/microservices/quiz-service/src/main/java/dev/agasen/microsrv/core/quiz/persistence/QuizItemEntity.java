package dev.agasen.microsrv.core.quiz.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table
@Entity(name="quiz_item")
public class QuizItemEntity {
  
  @Id @GeneratedValue
  private Long id;

  @Version
  private int version;

  private Long questionId;

  @Column(name="choice_id")
  private Long selectedChoiceId;
  
  @ManyToOne(fetch=FetchType.LAZY)
  private QuizEntity quiz;

}
