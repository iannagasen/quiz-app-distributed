package dev.agasen.microsrv.core.question.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

@Transactional
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
  
  List<QuestionEntity> findAllByTopic(String topic);

  List<QuestionEntity> findByIdIn(List<Long> ids);

}
