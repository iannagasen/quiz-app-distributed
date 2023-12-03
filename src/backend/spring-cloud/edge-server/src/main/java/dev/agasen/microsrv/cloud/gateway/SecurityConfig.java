package dev.agasen.microsrv.cloud.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // Support Reactive-base endpoints
public class SecurityConfig {
  
  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    /**
     * ServerhHttpSecurity is the same as HttpSecurity but for webflux
     * 
     * below expose /actuator/** as an unrestricted endpoint and others to be restricted
     *    - /actuator should be restricted in a production environment
     * 
     * .oauth2ResourceServer().jwt() specifies that authorization will be based 
     *    on OAuth 2.0 access tokens encoded as JWTs
     */
    http
      .authorizeExchange(c -> c
        .pathMatchers("/actuator/**").permitAll()
        .anyExchange().authenticated()
      )
      .oauth2ResourceServer(c -> c.jwt(Customizer.withDefaults()));

    return http.build();
  }

}
