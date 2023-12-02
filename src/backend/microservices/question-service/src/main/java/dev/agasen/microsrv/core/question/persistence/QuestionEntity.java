package dev.agasen.microsrv.core.question.persistence;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Entity
@Table(name="questions")
public class QuestionEntity {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(columnDefinition="INTEGER")
  private Long id;

  @Version
  private int version;

  private String topic;
  private String question;

  @OneToMany(
    mappedBy="question",
    orphanRemoval=true,
    cascade=CascadeType.ALL
  )
  private Set<ChoiceEntity> choices = new HashSet<>();

}
