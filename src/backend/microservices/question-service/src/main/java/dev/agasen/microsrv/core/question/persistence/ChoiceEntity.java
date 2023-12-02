package dev.agasen.microsrv.core.question.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name="choices")
public class ChoiceEntity {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(columnDefinition="INTEGER")
  private Long id;

  @Version
  private int version;

  private String value;
  private String explanation;
  private boolean correct;

  @ManyToOne(fetch=FetchType.LAZY)
  private QuestionEntity question;
}
