package dev.agasen.microsrv.manager.quiz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
@Slf4j
public class ReactiveSchedulerConfig {
  
  @Value("${app.scheduler.threadPoolSize}")
  private Integer threadPoolSize;

  @Value("${app.scheduler.taskQueueSize}")
  private Integer taskQueueSize;

  
  @Bean
  public Scheduler publishEventScheduler() {
    log.info("Creates a messagingScheduler with connectionPoolSize = {}", threadPoolSize);
		return Schedulers.newBoundedElastic(threadPoolSize, taskQueueSize, "publish-pool");
  }

}
