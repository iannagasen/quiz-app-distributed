package dev.agasen.microsrv.manager.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import dev.agasen.microsrv.api.proxies.QuestionServiceProxy;

@Configuration
public class ClientConfig {

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

}
