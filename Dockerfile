# Build the jar
FROM maven:3.9.11-eclipse-temurin-25-alpine AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Prepare runtime environment
FROM eclipse-temurin:25-jre-alpine-3.22

WORKDIR /app
COPY --from=build /app/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]