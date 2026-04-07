FROM maven:3.9-eclipse-temurin-11 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:resolve

COPY Worker.java src/main/java/Worker.java
RUN mvn clean compile assembly:single

FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/Worker-1.0-SNAPSHOT-jar-with-dependencies.jar ./worker.jar

CMD ["java", "-jar", "worker.jar"]
