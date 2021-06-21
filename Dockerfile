FROM openjdk:8-jdk-alpine

WORKDIR /

ARG JAR_FILE
COPY ${JAR_FILE} app.jar
RUN ls /

ENTRYPOINT ["java", "-jar", "/app.jar"]

