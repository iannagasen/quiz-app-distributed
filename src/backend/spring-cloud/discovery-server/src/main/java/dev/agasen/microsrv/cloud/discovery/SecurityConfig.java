package dev.agasen.microsrv.cloud.discovery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final String username;
  private final String password;

  public SecurityConfig(
    @Value("${app.eureka-username}") String username,
    @Value("${app.eureka-password}") String password
  ) {
    this.username = username;
    this.password = password;
  }


  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder()
        .username(username)
        .password(password)
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }


  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http
        // Disable CRCF to allow services to register themselves with Eureka
        .csrf(c -> c.disable())
        .authorizeHttpRequests(c -> c.anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }
  
}
