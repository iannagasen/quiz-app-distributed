package dev.agasen.microsrv.core.question.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.agasen.microsrv.api.core.question.Choice;
import dev.agasen.microsrv.core.question.persistence.ChoiceEntity;

@Mapper(componentModel="spring")
public interface ChoiceMapper {
  
  @Mapping(target="question", ignore=true)
  @Mapping(target="version", ignore=true)
  ChoiceEntity apiToEntity(Choice api);
  
  Choice entityToApi(ChoiceEntity entity);

}
