package dev.agasen.microsrv.manager.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
  
  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    /**
     * Resource Server Config
     * 
     * By convention, OAuth 2.0 scopes need to be prefixed with SCOPE_ 
     *    when checked for authority using Spring Security.
     *    EX: .hasAuthority("SCOPE_quiz:write")
     * 
     * TODO: 
     *  maybe separate the filter chain between the core application and as a resource server
     *    -- is this more readable???
     */
    http
      .authorizeExchange(c -> c
        // unrestric openapi and actuator resource
        .pathMatchers("/openapi/**").permitAll()
        .pathMatchers("/webjars/**").permitAll()
        .pathMatchers("/actuator/**").permitAll()
        // restrict using write and read scope
        .pathMatchers(HttpMethod.POST, "/manager/quiz/**").hasAuthority("SCOPE_quiz:write")
        .pathMatchers(HttpMethod.DELETE, "/manager/quiz/**").hasAuthority("SCOPE_quiz:write")
        .pathMatchers(HttpMethod.PATCH, "/manager/quiz/**").hasAuthority("SCOPE_quiz:write")
        .pathMatchers(HttpMethod.PUT, "/manager/quiz/**").hasAuthority("SCOPE_quiz:write")
        .pathMatchers(HttpMethod.GET, "/manager/quiz/**").hasAuthority("SCOPE_quiz:read")
        // others
        .anyExchange().authenticated()
      )
      .oauth2ResourceServer(c -> c.jwt(Customizer.withDefaults()));

    return http.build();
  }
}
