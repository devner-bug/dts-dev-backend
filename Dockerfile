FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY target/hmcts-task-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
