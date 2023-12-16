package dev.agasen.microsrv.cloud.auth;

import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import dev.agasen.microsrv.cloud.auth.jose.Jwks;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
  private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

 @Bean
  @Order(1)
  public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

    http
      .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
      .oidc(Customizer.withDefaults()); // enable OpenID Connect 1.0

    http
      // Redirect to the login page when not authenticated from the authorization endpoint
      .exceptionHandling(exceptions -> exceptions
        .defaultAuthenticationEntryPointFor(
          new LoginUrlAuthenticationEntryPoint("/login"), 
          new MediaTypeRequestMatcher(MediaType.TEXT_HTML)  
        )
      )
      .oauth2ResourceServer(resourceServer -> resourceServer
        .jwt(Customizer.withDefaults())
      );

    return http.build();
  }

  
  @Bean
  @Order(2)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/actuator/**").permitAll()
        .anyRequest().authenticated()
      )
      .formLogin(Customizer.withDefaults());

    return http.build();
  }


  @Bean
  public UserDetailsService users() {
    UserDetails user = User.withDefaultPasswordEncoder()
      .username("u")
      .password("p")
      .roles("USER")
      .build();
    return new InMemoryUserDetailsManager(user);
  }


  @Bean
  public RegisteredClientRepository registeredClientRepository() {
    RegisteredClient writerClient = RegisteredClient.withId(UUID.randomUUID().toString())
      .clientId("writer")
      .clientSecret("{noop}secret-writer")
      .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
      .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
      .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
      .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
      .redirectUri("https://my.redirect.uri")
      // this is configured in the gateway
      .redirectUri("https://localhost:8443/openapi/webjars/swagger-ui/oauth2-redirect.html")
      .redirectUri("http://localhost:4200")
      .scope(OidcScopes.OPENID)
      .scope("quiz:read")
      .scope("quiz:write")
      .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
      .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(1)).build())
      .build(); 

    RegisteredClient readerClient = RegisteredClient.withId(UUID.randomUUID().toString())
      .clientId("reader")
      .clientSecret("{noop}secret-reader")
      .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
      .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
      .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
      .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
      .redirectUri("https://my.redirect.uri")
      .redirectUri("https://localhost:8443/openapi/webjars/swagger-ui/oauth2-redirect.html")
      .redirectUri("http://localhost:4200")
      .scope(OidcScopes.OPENID)
      .scope("quiz:read")
      .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
      .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(1)).build())
      .build();

    return new InMemoryRegisteredClientRepository(writerClient, readerClient);
  }


  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = Jwks.generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }


  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  
  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
      // should be http://auth-server:9999 when using docker
      // parameterized this
      // value of the issuer must match resource server config of
      //    spring.security.oauth2.resourceserver.jwt.issuer-uri: http://${app.auth-server}:9999
      .issuer("http://localhost:9999")
      .build();
  }

}
