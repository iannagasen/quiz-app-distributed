package dev.agasen.microsrv.core.quiz.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.agasen.microsrv.api.core.quiz.Quiz;
import dev.agasen.microsrv.api.core.quiz.QuizItem;
import dev.agasen.microsrv.core.quiz.persistence.QuizEntity;
import dev.agasen.microsrv.core.quiz.persistence.QuizItemEntity;

@Mapper(componentModel="spring")
public interface QuizMapper {
  
  @Mapping(target="quizItems", source="questions")
  @Mapping(target="version", ignore=true)
  QuizEntity quizApiToEntity(Quiz api);

  @Mapping(target="questions", source="quizItems")
  Quiz quizEntityToApi(QuizEntity entity);


  @Mapping(target="version", ignore=true)
  QuizItemEntity quizItemApiToEntity(QuizItem api);

}
