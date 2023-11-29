FROM eclipse-temurin:17.0.5_8-jre-focal as builder
WORKDIR extracted
ADD ./build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.5_8-jre-focal
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./

EXPOSE 8080

# Define the entry point to run the Spring Boot application
# https://docs.spring.io/spring-boot/docs/3.2.0-SNAPSHOT/reference/html/executable-jar.html#appendix.executable-jar.launching.manifest
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
