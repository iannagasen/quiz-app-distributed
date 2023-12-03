## check all messages from a given topic
```bash
kafka-console-consumer.sh --bootstrap-server localhost:9094 --topic question-topic --from-beginning
```


## ROUTES
Local

Question Service: 7001

Quiz Service: 7002

Quiz Manager Service: 7000

Discovery Server: 8761

Edge Server: 8080

  - http://localhost:8080/manager/quiz/1
  - http://localhost:8080/question?topic=AWS
  - http://localhost:8080/openapi/webjars/swagger-ui/index.html
  - http://localhost:8080/eureka/web

Auth Server: 9999
  - http://localhost:9999/.well-known/openid-configuration?continue


### Create a Self Signed Certificate
```bash
keytool -genkeypair -alias localhost -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore edge.p12 -validity 3650
```
password

this will generate a edge.p12 file
transfer the file to the gateway, since gateway will be the only server using https
 - src/main/resources
create a folder named `keystore` under it and paste the edge.p12, this will be the path
 - src/main/resources/keystore/edge.p12

This means that the certificate file will be placed in the .jar file when it is built and will be available on the classpath at runtime at keystore/edge.p12.

NOTE:
**Providing certificates using the classpath is sufficient during development, but not applicable to other environments, for example, a production environment.**

Configure the edge server to use the certificate and HTTPS:

```yaml
# this was changed from 8080 to 8443 to indicate that we are using https not http
server.port: 8443 
server.ssl:
  key-store-type: PKCS12 
  key-store: classpath:keystore/edge.p12 
  key-store-password: password 
  key-alias: localhost
```

#### Replacing a self-signed certificate at runtime
- use certificates signed by authorized by **CA** (Certificate Authorities)

```bash
# apply where the docker compose is located
mkdir keystore
# use testtest as password
keytool -genkeypair -alias localhost -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore/edge-test.p12 -validity 3650
```

#### Configuring Resource Servers

ACTING AS RESOURCE SERVERS:
  - quiz-composite service
  - edge server

Required dependencies to support OAuth2.0 resource servers

```groovy
implementation 'org.springframework.boot:spring-boot-starter-security' 
implementation 'org.springframework.security:spring-security-oauth2resource-server' 
implementation 'org.springframework.security:spring-security-oauth2-jose'
```

config:

```java
@Configuration
@EnableWebFluxSecurity // support for API based on Spring WebFlux
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain( ServerHttpSecurity http) {
    http
    .authorizeExchange() 
      // allow unrestricted access to URLS that shound be unprotected
      // actuator should be protected in a production environment
      .pathMatchers("/actuator/**").permitAll()
      // below ensures that the user is authenticated before allowed acces to all other URLS
      .anyExchange().authenticated() 
      .and()
    // specifies that the authorization will be based on OAuth 2.0 access tokens encoded as JWTs
    .oauth2ResourceServer() 
      .jwt();
    return http.build(); 
  } 
}
```

add also this to app config

```yaml
# this should be change on dockerized environments
app.auth-server: localhost

spring.security.oauth2.resourceserver.jwt.issuer-uri: http://${app.authserver}:9999
```

#### Acquiring access tokens using the client credentials grant flow
```bash
curl -k https://writer:secret-writer@localhost:8443/oauth2/token -d grant_type=client_credentials -d scope="quiz:read quiz:write" -s | jq .
```
![Alt text](docs/screenshots/README/image.png)