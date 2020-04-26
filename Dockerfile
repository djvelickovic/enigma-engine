FROM openjdk:11-slim

COPY target/engine*.jar /application/service.jar

ENTRYPOINT java -jar /application/service.jar

