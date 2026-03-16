# Stage 1 - Build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy gradle files first (better layer caching)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Download dependencies first (cached if build.gradle doesn't change)
RUN ./gradlew dependencies --no-daemon

# Copy source code and build
COPY src src
RUN ./gradlew build --no-daemon -x test

# Stage 2 - Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]