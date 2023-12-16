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


https://localhost:8443/oauth2/authorize?response_type=code&client_id=reader&redirect_uri=https://my.redirect.uri&scope=quiz:read&state=35725

Next, get the authorization code in the URL

![Alt text](docs/screenshots/README/image-1.png)

and use the ff command:
  -k here means --insecure, allow transfer of data over HTTPS without verifying the SSL certificate
  -d for POST request, specify the data you want to send to the server
  -s silent mode

  

```sh
curl -k https://reader:secret-reader@localhost:8443/oauth2/token \
  -d grant_type=authorization_code \
  -d client_id=reader \
  -d redirect_uri=https://my.redirect.uri \
  -d code=AGS6LV-O0zxpqDUyNhT5KHsby3WdzZ3JN6Ktw--6A8kSoO3t3E7-pBF9bK1yVrSqBI3fp5p6NF_L2llM3c51g6DFcYIlo1XbnkUD_zKdGkIpBRng5m2wj3mTbGWDUVrj -s | jq .
```

Sample Output
![Alt text](docs/screenshots/README/image-2.png)

note here that there were no scope `quiz:write`, since we actually just get consent for quiz:read scope

to get a quiz:write consent use this

`https://localhost:8443/oauth2/authorize?response_type=code&client_id=writer&redirect_uri=https://my.redirect.uri&scope=quiz:read+quiz:write&state=72489`

and exchange the code for the token

```sh
curl -k https://writer:secret-writer@localhost:8443/oauth2/token \
  -d grant_type=authorization_code \
  -d client_id=writer \
  -d redirect_uri=https://my.redirect.uri \
  -d code=tVY_XlTSja0Blt-WEX_2TL3L_4tF5-zpH0Ko8wCMadloajtYwzgaqwcCMBsR5CPEuovou87qzGjFJanKkEeMWQeHHfxS-Ow67D6e9a22ZfAAxjJMfymaMCBpWzmrsJ6m | jq .
```

Output:

![Alt text](docs/screenshots/README/image-3.png)

Next, call a resource server using the accesst tokens
NOTE: An OAuth 2.0 access token is expected to be sent as a standard HTTP authorization header, where the access token is prefixed with Bearer.
```sh
ACCESS_TOKEN=an-invalid-token
curl https://localhost:8443/manager/quiz/1 -k -H "Authorization: Bearer eyJraWQiOiI3MzE1OTc3ZC04ZDcyLTRjMjYtYjIyMy0wZGZmMjNiOGE2OWMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1IiwiYXVkIjoicmVhZGVyIiwibmJmIjoxNzAxNzk2MTAyLCJzY29wZSI6WyJxdWl6OnJlYWQiXSwiaXNzIjoiaHR0cDovL2F1dGgtc2VydmVyOjk5OTkiLCJleHAiOjE3MDE3OTk3MDIsImlhdCI6MTcwMTc5NjEwMiwianRpIjoiZWIzMzBmZTgtZjc3MS00ZGNiLWEzMTAtMDYyNmM2ODI3MDM1In0.XRtdd02TYTnXK5oGCmReM55i6hKAwEKoL8xDxZl9AH55HWwYqXNunF8hLS-pIUaPjhfVul_DEk-wBpq4Hd9VKYp6tI2EbIst_Squ-9gH-PoeAFvr5GjKrwJTXIGqhMMlpzg23CLtfkxEb102E2nOGYU3JdVHvzBGus5JjXG96t_PGcVyaiLMj-NqVWlLAN6beb520Ecoy3eZ31Rn10j1Ilt8mJXjLb7q31aQEXG_w93ZjvU-P1hoF9y0kMgpZCr2TaEKnTIqFYrxU0WfIwkZjFKc_Tx38oT6_iSY43f8YphjuIoIsUnYIwiOtI5rWXA73MdKGd2mIYGq1RSLhQACrw" -i

curl https://localhost:8443/manager/quiz/1 -k -H "Authorization: Bearer $ACCESS_TOKEN" -i
curl https://localhost:8443/question?topic=AWS -k -H "Authorization: Bearer $ACCESS_TOKEN" -i
```
SAMPLE OUTPUT:
![Alt text](docs/screenshots/README/image-4.png)


![Alt text](docs/screenshots/README/image-5.png)
```sh
ACCESS_TOKEN=
```

Flow:
  1. call authorize endpoint to get the code 
```http
/authorize
  ?response_type=code
  &client_id=$REGISTERED_CLIENT_ID
  &redirect_uri=$WHERE_THE_SERVER_WILL_SEND_THE_CODE
  &scope=$WHAT_SCOPEGRANT_YOU_NEED
  &state=$RANDOM_TO_VALIDATE_THAT_IT_IS_YOURS

```
  2. you will be redirected in the redirect_uri, with the following query params: code, state. The code is the authorization code.
  3. exchange the authorization code for the access token
```sh
curl -k https://$CLIENT_ID:$CLIENT_SECRET@$AUTH_SERVER/oauth2/token \
  -d
```
