FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn -DskipTests clean package && cp target/*-SNAPSHOT.jar /tmp/app.jar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN mkdir -p /app/logs

COPY --from=build /tmp/app.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]