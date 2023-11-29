package dev.agasen.microsrv.manager.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import dev.agasen.microsrv.api.proxies.QuestionServiceProxy;
import dev.agasen.microsrv.api.proxies.QuizServiceProxy;

@Configuration
public class ClientConfig {

  // TODO:
  // URL should be parameterized, probably via Dependency injection
  // When converting to Service Discovery, dont forget to Load Balance the WebClient

  @Bean
  public WebClient.Builder webClient() {
    return WebClient.builder();
  }

  @Bean
  public QuestionServiceProxy questionServiceProxy() {
    WebClient webClient = webClient()
        .baseUrl("http://localhost:7001") 
        .build();

    return HttpServiceProxyFactory
        .builderFor(WebClientAdapter.create(webClient))
        .build()
        .createClient(QuestionServiceProxy.class);
  }

  @Bean
  public QuizServiceProxy quizServiceProxy() {
    WebClient webClient = webClient()
        .baseUrl("http://localhost:7002")
        .build();

    return HttpServiceProxyFactory
        .builderFor(WebClientAdapter.create(webClient))
        .build()
        .createClient(QuizServiceProxy.class);
  }

}
