# Build the jar
FROM maven:latest AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Prepare runtime environment
FROM openjdk:25-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]