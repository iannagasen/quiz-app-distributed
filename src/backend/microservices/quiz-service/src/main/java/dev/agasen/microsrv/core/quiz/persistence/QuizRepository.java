package dev.agasen.microsrv.core.quiz.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
  
}
