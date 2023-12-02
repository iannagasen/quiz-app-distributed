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