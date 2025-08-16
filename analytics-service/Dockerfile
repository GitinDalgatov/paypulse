# Stage 1: Build
FROM gradle:8.5-jdk21 AS build
WORKDIR /build
COPY . .
RUN gradle :analytics-service:build -x test

# Stage 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN apt-get update && apt-get install -y curl
COPY --from=build /build/analytics-service/build/libs/*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/app.jar"]