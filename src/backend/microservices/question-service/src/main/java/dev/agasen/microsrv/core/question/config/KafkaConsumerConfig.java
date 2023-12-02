package dev.agasen.microsrv.core.question.config;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;

import java.util.Map;

import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.fasterxml.jackson.core.type.TypeReference;

import dev.agasen.microsrv.api.core.question.Question;
import dev.agasen.microsrv.api.event.Event;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  @Bean
  public ConsumerFactory<Long, Event<Long, Question>> consumerFactory() {
    var configs = Map.<String, Object>of(
      BOOTSTRAP_SERVERS_CONFIG, "localhost:9094",
      GROUP_ID_CONFIG, "question-group",
      ENABLE_AUTO_COMMIT_CONFIG, false,
      SESSION_TIMEOUT_MS_CONFIG, 15000,
      "spring.json.trusted.packages", "*",
      "spring.json.use.type.headers", false
    );

    return new DefaultKafkaConsumerFactory<>(configs, new LongDeserializer(), new JsonDeserializer<>(new TypeReference<Event<Long, Question>>() {}, false));
  }

  @Bean
  public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<Long, Event<Long, Question>> factory =
          new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
  
}
