package dev.agasen.microsrv.core.question.service;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import dev.agasen.microsrv.api.core.question.Question;
import dev.agasen.microsrv.core.question.persistence.QuestionEntity;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
  
  Question entityToApi(QuestionEntity entity);

  @Mappings({
    @Mapping(target="version", ignore=true)
  })
  QuestionEntity apiToEntity(Question api);

  List<Question> entityListToApiList(List<QuestionEntity> entity);

  List<QuestionEntity> apiListToEntityList(List<Question> api);
  
}
