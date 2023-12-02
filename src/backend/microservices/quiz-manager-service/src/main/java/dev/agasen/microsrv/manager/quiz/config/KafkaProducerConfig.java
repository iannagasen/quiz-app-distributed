package dev.agasen.microsrv.manager.quiz.config;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import dev.agasen.microsrv.api.core.question.Question;
import dev.agasen.microsrv.api.event.Event;

@Configuration
public class KafkaProducerConfig {


  @Bean
  public ProducerFactory<Long, Event<Long, Question>> producerFactory() {
    var config = Map.<String, Object>of(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094",
      ProducerConfig.RETRIES_CONFIG, 0,
      ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432
    );
    return new DefaultKafkaProducerFactory<>(
       config, new LongSerializer(), new JsonSerializer<Event<Long, Question>>()
    );
  }


  
  @Bean
  public KafkaTemplate<Long, Event<Long, Question>> kafkaTemplate(
      ProducerFactory<Long, Event<Long, Question>> producerFactory
  ) {

    return new KafkaTemplate<>(producerFactory);
  }
}
