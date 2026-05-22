# Stage 1: Build avec Maven et Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY common-lib ./common-lib
COPY announce-manager .

RUN mvn -f common-lib/pom.xml clean install -DskipTests
RUN mvn clean package -DskipTests


# Stage 2: Runtime minimal avec JRE 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Configuration pour Graylog (GELF) et DB via variables d'env
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/annocedb
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres

ENTRYPOINT ["java", "-jar", "app.jar"]