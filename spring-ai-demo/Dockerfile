# Stage 1 — Build
# eclipse-temurin:21-jdk-alpine ~220MB
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Tell Gradle to use the container's Java 21 instead of looking for Java 25
ENV JAVA_HOME=/opt/java/openjdk
ENV JAVA_TOOL_OPTIONS="-Dorg.gradle.java.home=/opt/java/openjdk"

# Copy gradle wrapper + config (cached layer)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Make gradlew executable (needed on Linux)
RUN chmod +x gradlew

# Pre-download dependencies (cached layer — reused unless build.gradle changes)
RUN ./gradlew dependencies --no-daemon \
    -Porg.gradle.java.home=$JAVA_HOME || true

# Copy source and build, overriding the toolchain version
COPY src src
RUN ./gradlew clean build -x test --no-daemon \
    -Porg.gradle.java.home=$JAVA_HOME

# Stage 2 — Smallest runtime image
# eclipse-temurin:21-jre-alpine ~85MB
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]