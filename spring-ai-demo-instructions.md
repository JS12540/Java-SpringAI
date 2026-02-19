# 🤖 Spring AI Demo — End-to-End Guide

A Spring Boot application integrated with OpenAI's GPT models using Spring AI, built with Gradle.

---

## 📁 Project Structure

```
spring-ai-demo/
├── src/
│   ├── main/
│   │   ├── java/com/example/spring_ai_demo/
│   │   │   ├── SpringAiDemoApplication.java   # Entry point
│   │   │   └── AiController.java              # REST controller
│   │   └── resources/
│   │       └── application.yaml               # Config (model, API key)
│   └── test/
│       └── java/com/example/spring_ai_demo/
│           └── SpringAiDemoApplicationTests.java
├── build.gradle        # Gradle build config (dependencies, plugins)
├── gradlew             # Gradle wrapper (Unix)
├── gradlew.bat         # Gradle wrapper (Windows)
├── settings.gradle     # Project name
├── Dockerfile          # Docker config
└── .gitignore          # Files to exclude from Git
```

---

## ⚙️ Prerequisites

- Java 17+ (project uses Java 25)
- Gradle (via wrapper — no install needed)
- An OpenAI API Key → [platform.openai.com/api-keys](https://platform.openai.com/api-keys)

---

## 🔑 OpenAI API Key Setup

### Option 1 — Hardcode (Local Dev Only, DO NOT commit)

In `src/main/resources/application.yaml`:

```yaml
spring:
  ai:
    openai:
      api-key: sk-proj-xxxxxxxxxxxxxxxx
      chat:
        options:
          model: gpt-4.1-mini
```

### Option 2 — Environment Variable (Recommended)

In `application.yaml`:

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4.1-mini
```

Set the variable in PowerShell (Windows):

```powershell
$env:OPENAI_API_KEY = "sk-proj-xxxxxxxxxxxxxxxx"
```

Set the variable in bash/zsh (Mac/Linux):

```bash
export OPENAI_API_KEY="sk-proj-xxxxxxxxxxxxxxxx"
```

> ⚠️ Environment variables reset when you close the terminal. Add the export to your `~/.bashrc` or `~/.zshrc` to make it permanent.

---

## 📦 Gradle — Dependencies & Build Config

`build.gradle` key sections:

```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '4.0.2'
    id 'io.spring.dependency-management' version '1.1.7'
}

ext {
    springAiVersion = "2.0.0-M2"
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation platform("org.springframework.ai:spring-ai-bom:${springAiVersion}")
    implementation 'org.springframework.ai:spring-ai-starter-model-openai'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

Key dependencies explained:

| Dependency | Purpose |
|---|---|
| `spring-boot-starter-web` | Enables REST controllers, embedded Tomcat |
| `spring-ai-starter-model-openai` | Spring AI's OpenAI integration |
| `spring-ai-bom` | Bill of Materials — manages Spring AI versions |
| `lombok` | Reduces boilerplate (getters, setters, etc.) |
| `spring-boot-devtools` | Auto-restart on code changes during dev |
| `spring-boot-starter-test` | JUnit 5 + Spring test support |

---

## 🚀 Running the App

### Build

```powershell
.\gradlew clean build
```

### Run

```powershell
.\gradlew bootRun
```

### Test the endpoint

Open browser or use curl:

```bash
# Default prompt
http://localhost:8080/ask

# Custom prompt
http://localhost:8080/ask?message=Explain dependency injection
```

---

## 🧠 How Gradle Works — In Depth (Simple Language)

### Gradle is to Java what pyproject.toml is to Python

In Python, `pyproject.toml` (or `requirements.txt`) defines your project — its name, dependencies like `requests` or `flask`, and how to run or test it. Gradle does the exact same job for Java, but it also automates the entire **build lifecycle** — compiling, testing, packaging, and running your app.

---

### The Core Idea — Tasks

Everything in Gradle is a **task**. A task is just a unit of work, like:

- `compileJava` → turns `.java` files into `.class` bytecode
- `test` → runs your JUnit tests
- `jar` → packages everything into a `.jar` file
- `bootRun` → starts your Spring Boot app
- `clean` → deletes the `build/` folder

When you run `.\gradlew clean build`, Gradle runs these tasks **in the correct order automatically**.

```
clean → compileJava → processResources → classes → compileTestJava → test → jar → build
```

---

### The Gradle Wrapper (`gradlew`)

You never need to install Gradle globally. The project ships with a **wrapper** (`gradlew` / `gradlew.bat`) that:

1. Checks which Gradle version the project needs (defined in `gradle/wrapper/gradle-wrapper.properties`)
2. Downloads that exact version automatically if not cached
3. Runs it

This guarantees every developer and CI/CD system uses the **same Gradle version** — no "works on my machine" issues.

---

### `build.gradle` — The Heart of Gradle

This file is your project's brain. It's written in **Groovy** (or Kotlin) and has these key sections:

```
plugins       → what tools/frameworks to use (Spring Boot plugin, Java plugin)
repositories  → where to download dependencies from (Maven Central = npm registry equivalent)
dependencies  → what libraries your code needs
tasks         → custom build steps
java          → Java version config
```

---

### Dependency Scopes

Not all dependencies are equal. Gradle uses **scopes** to decide when a dependency is needed:

| Scope | When Used | Python Equivalent |
|---|---|---|
| `implementation` | Compile + runtime | Regular dependency in `pyproject.toml` |
| `compileOnly` | Only during compilation, not at runtime | Dev/type hint dependency |
| `testImplementation` | Only during tests | `[test]` extras |
| `developmentOnly` | Only during local dev | Dev dependency |
| `runtimeOnly` | Only at runtime, not compile time | Runtime-only package |

---

### Bill of Materials (BOM)

```groovy
implementation platform("org.springframework.ai:spring-ai-bom:${springAiVersion}")
```

A BOM is like a **version lock file** (similar to `poetry.lock` in Python). Instead of specifying versions for every Spring AI library individually, you import the BOM once and it manages all compatible versions automatically. No more version conflicts.

---

### The Build Lifecycle — Step by Step

```
1. Initialization  → Gradle reads settings.gradle, finds the project name
2. Configuration   → Gradle reads build.gradle, sets up all tasks and their dependencies
3. Execution       → Gradle runs the requested tasks in the right order
```

When you run `.\gradlew build`:

```
📂 Source code (.java files)
        ↓ compileJava
📂 Bytecode (.class files in build/classes/)
        ↓ processResources
📂 Resources copied (application.yaml, etc.)
        ↓ test
✅ Tests run (JUnit)
        ↓ jar
📦 Packaged JAR (build/libs/spring-ai-demo-0.0.1-SNAPSHOT.jar)
        ↓ build
✅ BUILD SUCCESSFUL
```

---

### Incremental Builds — Gradle is Smart

Gradle tracks what has changed. If you run `.\gradlew build` twice and change nothing, the second run is near-instant because Gradle skips tasks whose inputs/outputs haven't changed. This is called **incremental building** — a major advantage over older tools like Maven.

---

### Gradle vs Maven vs pip vs pyproject

| Feature | Gradle | Maven | pip/pyproject |
|---|---|---|---|
| Language | Groovy/Kotlin DSL | XML | TOML/plain text |
| Speed | Fast (incremental) | Slower | Fast |
| Flexibility | Very high | Rigid | Moderate |
| Wrapper | Yes (`gradlew`) | Yes (`mvnw`) | No |
| Standard in | Android, Spring | Enterprise Java | Python |

---

## 🐙 Pushing to GitHub

### 1. Create `.gitignore`

Create `.gitignore` in your project root:

```gitignore
# Gradle
.gradle/
build/

# IDE
.vscode/
.idea/
*.iml

# Secrets — NEVER commit these
application-local.yaml
.env

# OS
.DS_Store
Thumbs.db
```

> ⚠️ If your `application.yaml` contains a hardcoded API key, either switch to `${OPENAI_API_KEY}` env variable, or add `application.yaml` to `.gitignore` and create an `application.yaml.example` with placeholder values to commit instead.

### 2. Initialize Git and push

```bash
# Initialize repo
git init

# Stage all files
git add .

# Commit
git commit -m "Initial commit — Spring AI demo"

# Create repo on GitHub (github.com → New Repository)
# Then link and push:
git remote add origin https://github.com/YOUR_USERNAME/spring-ai-demo.git
git branch -M main
git push -u origin main
```

### 3. Verify no secrets leaked

Before pushing, double-check:

```bash
git diff --cached | grep "sk-"
```

If anything shows up, remove the key and use the env variable approach instead.

---

## 🐳 Docker — Dockerize & Run

### `Dockerfile`

```dockerfile
# Stage 1 — Build
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Stage 2 — Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Build the Docker image

```bash
docker build -t spring-ai-demo .
```

### Run the container

```bash
docker run -p 8080:8080 \
  -e OPENAI_API_KEY=sk-proj-xxxxxxxxxxxxxxxx \
  spring-ai-demo
```

### Test it

```bash
curl "http://localhost:8080/ask?message=Hello from Docker"
```

### Docker Compose (Optional)

Create `docker-compose.yml`:

```yaml
version: '3.8'
services:
  spring-ai-demo:
    build: .
    ports:
      - "8080:8080"
    environment:
      - OPENAI_API_KEY=${OPENAI_API_KEY}
```

Run with:

```bash
docker compose up
```

---

## 📋 Quick Reference

| Command | What it does |
|---|---|
| `.\gradlew clean build` | Clean + compile + test + package |
| `.\gradlew bootRun` | Run the Spring Boot app |
| `.\gradlew test` | Run tests only |
| `.\gradlew dependencies` | Show all resolved dependencies |
| `.\gradlew tasks` | List all available tasks |
| `docker build -t spring-ai-demo .` | Build Docker image |
| `docker run -p 8080:8080 -e OPENAI_API_KEY=... spring-ai-demo` | Run container |

---

## 🔗 Useful Links

- [Spring AI Docs](https://docs.spring.io/spring-ai/reference/)
- [OpenAI API Keys](https://platform.openai.com/api-keys)
- [Gradle Docs](https://docs.gradle.org)
- [Spring Initializr](https://start.spring.io)
