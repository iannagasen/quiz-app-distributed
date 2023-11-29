package dev.agasen.microsrv.core.quiz.persistence;



import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table
@Entity(name="quiz")
public class QuizEntity {
  
  @Id @GeneratedValue
  private Long id;

  @Version
  private int version;

  @OneToMany(
    mappedBy="quiz",
    orphanRemoval=true,
    cascade=CascadeType.ALL
  )
  private List<QuizItemEntity> quizItems;
  
}
