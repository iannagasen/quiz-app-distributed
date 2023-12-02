package dev.agasen.microsrv.core.question.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import dev.agasen.microsrv.api.core.question.Question;
import dev.agasen.microsrv.api.core.question.QuestionService;
import dev.agasen.microsrv.api.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuestionKafkaListener {
  
  private final QuestionService questionService;

  @KafkaListener(topics={ "question-topic" }, groupId="question-group", containerFactory="kafkaListenerContainerFactory")
  public void consume(ConsumerRecord<Long, Event<Long, Question>> record) {
    
    log.info("QuestionKafkaListener::consume with key: {}", record.key());
    log.info("testing. {}", record.value().toString());
    var value = record.value();
    switch (value.getEventType()) {
      
      case CREATE:
        questionService.createQuestion(value.getData());
        break;
    
      // TODO:
      // implement delete

      default:
        throw new RuntimeException("Incorrect event type: " + value.getEventType() + ", expected a CREATE or DELETE event");
    }
  }
}
