# ☕ Java Project Setup — The Complete Engineer's Guide

Everything you need to know about starting, building, and cloning Java Spring Boot projects like a principal engineer.

---

## Table of Contents

1. [How to Start Any Java Project](#1-how-to-start-any-java-project)
2. [Should You Always Use start.spring.io?](#2-should-you-always-use-startspringio)
3. [Project Generation by IDE](#3-project-generation-by-ide)
4. [Where to Find Java Dependencies](#4-where-to-find-java-dependencies)
5. [Common Dependencies Reference List](#5-common-dependencies-reference-list)
6. [How a Principal Engineer Builds a Gradle Project](#6-how-a-principal-engineer-builds-a-gradle-project)
7. [Cloning & Setting Up on a New Laptop](#7-cloning--setting-up-on-a-new-laptop)

---

## 1. How to Start Any Java Project

Starting a Java project is not just "download and run." A senior engineer thinks through these questions **before writing a single line of code**:

### Step 1 — Decide What You're Building

| Type | Framework | Use Case |
|---|---|---|
| REST API / Web App | Spring Boot | Most backend services |
| Microservice | Spring Boot + Spring Cloud | Distributed systems |
| Batch Processing | Spring Batch | ETL, data pipelines |
| CLI Tool | Plain Java / Picocli | Command line utilities |
| Library/SDK | Plain Java | Shared code, no server |
| Android App | Android SDK | Mobile |

For most backend work → **Spring Boot is the industry standard.**

### Step 2 — Decide Your Build Tool

| Tool | Use When |
|---|---|
| **Gradle** | New projects, Android, faster builds, more flexible |
| **Maven** | Legacy enterprise codebases, strict XML config |

> 💡 If starting fresh today, always pick **Gradle**. It's faster, more readable, and the modern standard.

### Step 3 — Decide Your Java Version

| Version | Status | Use When |
|---|---|---|
| Java 8 | Old LTS | Only for ancient legacy projects |
| Java 11 | LTS | Older enterprise projects |
| Java 17 | LTS ✅ | Safe default for most new projects |
| Java 21 | LTS ✅ | Best choice today — virtual threads, modern features |
| Java 25 | Preview | Cutting edge, avoid in production |

> 💡 Always pick an **LTS (Long Term Support)** version for production. Use **Java 21** for new projects today.

### Step 4 — Generate the Project Skeleton

You have multiple ways to do this (covered in detail in sections 2 and 3).

### Step 5 — Verify Your Local Setup

Before anything, confirm Java is installed:

```bash
java -version
```

Expected output:
```
openjdk version "21.0.x" ...
```

If not installed, download from:
- [adoptium.net](https://adoptium.net) — Temurin JDK (most popular, free)
- [oracle.com/java](https://www.oracle.com/java/technologies/downloads/) — Oracle JDK

---

## 2. Should You Always Use start.spring.io?

**Short answer: No. But it's the best starting point for most cases.**

### What is start.spring.io?

It's the official **Spring Initializr** — a web UI that generates a ready-to-run Spring Boot project skeleton with your chosen dependencies, Java version, and build tool. You download a ZIP, extract, and start coding.

### When TO use start.spring.io

- Starting a brand new Spring Boot project from scratch
- You want a clean, correctly configured `build.gradle` without writing it manually
- You want to quickly experiment with new Spring dependencies
- You're a beginner or joining a new domain (Spring AI, Spring Security, etc.)

### When NOT to use start.spring.io

| Scenario | What to do instead |
|---|---|
| Adding to an existing project | Manually add dependencies to `build.gradle` |
| Non-Spring Java project (plain Java, CLI tool) | Use your IDE's built-in project wizard |
| Company has a project archetype/template | Use the internal template |
| You need a very custom setup | Start from scratch manually |

### The Golden Rule

> Use **start.spring.io** to get the skeleton right, then customize `build.gradle` for everything else.

---

## 3. Project Generation by IDE

### Option A — start.spring.io (Browser, Any IDE)

1. Go to [start.spring.io](https://start.spring.io)
2. Configure:
   - **Project:** Gradle - Groovy
   - **Language:** Java
   - **Spring Boot:** Latest stable (not SNAPSHOT)
   - **Java:** 21
3. Click **ADD DEPENDENCIES** → search and add what you need
4. Click **GENERATE** → downloads a ZIP
5. Extract and open in your IDE

---

### Option B — IntelliJ IDEA (Best Java IDE)

IntelliJ has Spring Initializr **built in** — no browser needed.

**Steps:**
1. Open IntelliJ → `File` → `New` → `Project`
2. Select **Spring Boot** from the left panel
3. Fill in:
   - Name, Group (`com.yourcompany`), Artifact, Package name
   - Language: Java
   - Build system: Gradle - Groovy (or Kotlin)
   - JDK: 21
4. Click **Next** → search and add dependencies
5. Click **Create**

IntelliJ automatically imports the Gradle project and downloads all dependencies.

> 💡 **IntelliJ Ultimate** has the Spring Boot plugin built in. **IntelliJ Community** (free) requires you to use start.spring.io and import the project.

---

### Option C — VS Code

VS Code is a code editor, not a full IDE. You need extensions first.

**Install these extensions:**
- `Extension Pack for Java` (Microsoft) — core Java support
- `Spring Boot Extension Pack` (VMware) — Spring-specific tools
- `Gradle for Java` (Microsoft) — Gradle support

**Generate project in VS Code:**
1. Open Command Palette → `Ctrl+Shift+P`
2. Type: `Spring Initializr: Create a Gradle Project`
3. Follow the prompts (Spring Boot version, language, Java version, dependencies)
4. Choose save location → project opens automatically

**Or** use start.spring.io, download ZIP, then:
1. `File` → `Open Folder` → select extracted project
2. VS Code detects it's a Java/Gradle project and prompts to import

---

### Option D — Command Line (No IDE)

Using Spring Boot CLI:

```bash
# Install Spring CLI via brew (Mac)
brew install spring-io/tap/spring-boot

# Generate project
spring init \
  --dependencies=web,data-jpa,lombok \
  --build=gradle \
  --java-version=21 \
  --name=my-project \
  my-project.zip

unzip my-project.zip
cd my-project
```

---

### Option E — Manually from Scratch (Principal Engineer Style)

Create the folder structure yourself:

```bash
mkdir my-project && cd my-project
mkdir -p src/main/java/com/example/myproject
mkdir -p src/main/resources
mkdir -p src/test/java/com/example/myproject
touch build.gradle settings.gradle
touch src/main/resources/application.yaml
```

Then write `build.gradle` from scratch (see Section 6 for how a principal engineer does this).

---

## 4. Where to Find Java Dependencies

### Primary Source — Maven Central

**[search.maven.org](https://search.maven.org)** is the npm registry of the Java world. Every public Java library lives here.

**How to search:**
1. Go to [search.maven.org](https://search.maven.org)
2. Search by library name e.g. `jackson`, `lombok`, `flyway`
3. Click on the result → copy the Gradle dependency string

Example — searching for Jackson:
```
Search: "jackson-databind"
Result page shows:
  Group: com.fasterxml.jackson.core
  Artifact: jackson-databind
  Latest version: 2.17.1

Gradle snippet:
  implementation 'com.fasterxml.jackson.core:jackson-databind:2.17.1'
```

---

### Alternative Sources

| Source | URL | Use When |
|---|---|---|
| **Maven Central** | search.maven.org | 99% of the time — primary source |
| **MVN Repository** | mvnrepository.com | More human-friendly UI, usage stats |
| **Spring Docs** | docs.spring.io | For Spring-specific starters |
| **GitHub Packages** | github.com | Private/internal libraries |
| **JitPack** | jitpack.io | Libraries only on GitHub, not published to Maven Central |

---

### How to Read a Gradle Dependency String

```groovy
implementation 'com.fasterxml.jackson.core:jackson-databind:2.17.1'
                ^----- Group ID --------^  ^-- Artifact ID --^  ^ver^
```

- **Group ID** — the organization/package namespace (like a Python package author)
- **Artifact ID** — the specific library (like the Python package name)
- **Version** — the specific release

If you use a **BOM (Bill of Materials)** like Spring Boot's dependency management, you can often **omit the version** because the BOM manages it for you:

```groovy
// With Spring Boot BOM managing versions — no version needed
implementation 'org.springframework.boot:spring-boot-starter-web'

// Without BOM — must specify version manually
implementation 'org.springframework.boot:spring-boot-starter-web:3.3.0'
```

---

### Finding the Right Version

Always check:
1. **Is it the latest stable?** — Avoid `SNAPSHOT` and `M1/M2` (milestone) versions in production
2. **Is it compatible with your Spring Boot version?** — Check the [Spring Boot compatibility matrix](https://spring.io/projects/spring-boot#learn)
3. **How many downloads/stars?** — MVN Repository shows download trends

---

## 5. Common Dependencies Reference List

### Web & REST

| Dependency | Gradle String | Use Case |
|---|---|---|
| Spring Web | `spring-boot-starter-web` | REST APIs, embedded Tomcat |
| Spring WebFlux | `spring-boot-starter-webflux` | Reactive/async APIs |
| Spring Validation | `spring-boot-starter-validation` | Request body validation (`@Valid`, `@NotNull`) |

### Data & Persistence

| Dependency | Gradle String | Use Case |
|---|---|---|
| Spring Data JPA | `spring-boot-starter-data-jpa` | ORM with Hibernate, SQL databases |
| Spring Data MongoDB | `spring-boot-starter-data-mongodb` | MongoDB integration |
| Spring Data Redis | `spring-boot-starter-data-redis` | Redis caching/sessions |
| PostgreSQL Driver | `org.postgresql:postgresql` | Connect to PostgreSQL |
| MySQL Driver | `com.mysql:mysql-connector-j` | Connect to MySQL |
| H2 Database | `com.h2database:h2` | In-memory DB for tests |
| Flyway | `org.flywaydb:flyway-core` | Database migrations |
| Liquibase | `org.liquibase:liquibase-core` | Database migrations (alternative) |

### Security

| Dependency | Gradle String | Use Case |
|---|---|---|
| Spring Security | `spring-boot-starter-security` | Authentication, authorization |
| Spring OAuth2 | `spring-boot-starter-oauth2-resource-server` | JWT / OAuth2 API security |
| Spring OAuth2 Client | `spring-boot-starter-oauth2-client` | Login with Google, GitHub, etc. |

### Developer Tools & Utilities

| Dependency | Gradle String | Use Case |
|---|---|---|
| Lombok | `org.projectlombok:lombok` | Reduces boilerplate (`@Getter`, `@Builder`, etc.) |
| DevTools | `spring-boot-devtools` | Auto-restart on code change |
| Actuator | `spring-boot-starter-actuator` | Health checks, metrics endpoints |
| MapStruct | `org.mapstruct:mapstruct` | Object mapping (DTO ↔ Entity) |

### Messaging & Events

| Dependency | Gradle String | Use Case |
|---|---|---|
| Spring Kafka | `spring-kafka` | Apache Kafka producer/consumer |
| Spring AMQP (RabbitMQ) | `spring-boot-starter-amqp` | RabbitMQ messaging |
| Spring WebSocket | `spring-boot-starter-websocket` | Real-time WebSocket connections |

### Testing

| Dependency | Gradle String | Use Case |
|---|---|---|
| Spring Test | `spring-boot-starter-test` | JUnit 5, Mockito, MockMvc |
| Testcontainers | `org.testcontainers:junit-jupiter` | Real DBs in Docker during tests |
| WireMock | `org.wiremock:wiremock-standalone` | Mock external HTTP APIs in tests |
| AssertJ | `org.assertj:assertj-core` | Fluent test assertions |

### Observability

| Dependency | Gradle String | Use Case |
|---|---|---|
| Micrometer | `io.micrometer:micrometer-core` | Application metrics |
| Zipkin / Sleuth | `io.micrometer:micrometer-tracing` | Distributed tracing |
| Logback | Built into Spring Boot | Logging (default) |

### AI / ML

| Dependency | Gradle String | Use Case |
|---|---|---|
| Spring AI OpenAI | `spring-ai-starter-model-openai` | OpenAI GPT integration |
| Spring AI Ollama | `spring-ai-starter-model-ollama` | Local LLMs via Ollama |
| Spring AI Vector Store | `spring-ai-starter-vector-store-pgvector` | RAG / vector search |

### API Documentation

| Dependency | Gradle String | Use Case |
|---|---|---|
| SpringDoc OpenAPI | `org.springdoc:springdoc-openapi-starter-webmvc-ui` | Auto Swagger UI at `/swagger-ui.html` |

---

## 6. How a Principal Engineer Builds a Gradle File & Starts a Project

A principal engineer doesn't just click buttons on start.spring.io and hope for the best. Here's the full thought process and workflow:

### Phase 1 — Think Before You Type

Answer these before opening your IDE:

```
What problem am I solving?
  → REST API for a chat application using OpenAI

What external systems does it talk to?
  → OpenAI API, PostgreSQL database, Redis cache

What are the non-functional requirements?
  → Must be observable (metrics), secure (JWT), containerized (Docker)

What Java version?
  → Java 21 (LTS, virtual threads available)

What Spring Boot version?
  → Latest stable (check spring.io for current stable release)
```

### Phase 2 — Generate the Skeleton

Use start.spring.io or IntelliJ to generate with just the minimal core dependencies. Don't add 15 things you might not need.

**Minimum to start:**
- Spring Web
- Spring Boot DevTools
- Lombok

Add everything else as you actually need it.

### Phase 3 — Write a Professional `build.gradle`

Here's how a principal engineer structures it — clean, documented, organized:

```groovy
// ============================================================
// PLUGINS
// ============================================================
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.3.0'
    id 'io.spring.dependency-management' version '1.1.5'
}

// ============================================================
// PROJECT METADATA
// ============================================================
group = 'com.example'
version = '1.0.0'
description = 'Spring AI Demo — OpenAI chat integration'

// ============================================================
// JAVA VERSION
// ============================================================
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

// ============================================================
// DEPENDENCY VERSIONS (manage in one place)
// ============================================================
ext {
    springAiVersion = '1.0.0'
    lombokVersion = '1.18.32'
    mapstructVersion = '1.5.5.Final'
}

// ============================================================
// REPOSITORIES
// ============================================================
repositories {
    mavenCentral()
    // Add Spring milestones repo only if using SNAPSHOT/M versions
    // maven { url 'https://repo.spring.io/milestone' }
}

// ============================================================
// CONFIGURATIONS
// ============================================================
configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

// ============================================================
// DEPENDENCIES
// ============================================================
dependencies {

    // --- Core Web ---
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-validation'

    // --- Spring AI ---
    implementation platform("org.springframework.ai:spring-ai-bom:${springAiVersion}")
    implementation 'org.springframework.ai:spring-ai-starter-model-openai'

    // --- Data ---
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'org.postgresql:postgresql'

    // --- Security ---
    implementation 'org.springframework.boot:spring-boot-starter-security'

    // --- Observability ---
    implementation 'org.springframework.boot:spring-boot-starter-actuator'

    // --- API Docs ---
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0'

    // --- Developer Tools ---
    compileOnly "org.projectlombok:lombok:${lombokVersion}"
    annotationProcessor "org.projectlombok:lombok:${lombokVersion}"
    developmentOnly 'org.springframework.boot:spring-boot-devtools'

    // --- Testing ---
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    testImplementation 'com.h2database:h2' // in-memory DB for tests
}

// ============================================================
// TEST CONFIGURATION
// ============================================================
tasks.named('test') {
    useJUnitPlatform()
    // Show test results in terminal
    testLogging {
        events "passed", "skipped", "failed"
    }
}

// ============================================================
// JAR CONFIGURATION
// ============================================================
jar {
    // Disable plain jar — only produce the fat executable jar
    enabled = false
}
```

### Phase 4 — Set Up `application.yaml` Properly

A principal engineer separates config by environment:

```
src/main/resources/
├── application.yaml              # shared defaults
├── application-local.yaml        # local dev overrides (gitignored)
├── application-dev.yaml          # dev environment
└── application-prod.yaml         # production
```

`application.yaml`:
```yaml
spring:
  application:
    name: spring-ai-demo
  profiles:
    active: ${SPRING_PROFILE:local}   # default to local if not set

server:
  port: 8080

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

`application-local.yaml` (gitignored):
```yaml
spring:
  ai:
    openai:
      api-key: sk-proj-your-actual-key
      chat:
        options:
          model: gpt-4.1-mini
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: postgres
    password: postgres
```

### Phase 5 — Project Conventions a Principal Engineer Sets Up

Before writing business logic, set up:

```
✅ .gitignore — exclude build/, .gradle/, secrets
✅ README.md — how to run, what env vars are needed
✅ Dockerfile — containerization from day 1
✅ .env.example — document required env vars without values
✅ Logging config — structured JSON logs for production
✅ Exception handler — @ControllerAdvice for consistent error responses
✅ Health check — /actuator/health working
✅ Package structure — organized by feature, not by layer
```

### Phase 6 — Package Structure (Feature-Based, Not Layer-Based)

❌ **Avoid this (layer-based — hard to scale):**
```
com.example.myapp/
├── controllers/
├── services/
├── repositories/
└── models/
```

✅ **Do this (feature-based — scales well):**
```
com.example.myapp/
├── chat/
│   ├── ChatController.java
│   ├── ChatService.java
│   ├── ChatRepository.java
│   └── ChatMessage.java
├── user/
│   ├── UserController.java
│   ├── UserService.java
│   └── User.java
└── common/
    ├── GlobalExceptionHandler.java
    └── ApiResponse.java
```

---

## 7. Cloning & Setting Up on a New Laptop

Using `spring-ai-demo` as the example — here's exactly what to do step by step.

### Prerequisites — Install on the New Laptop

**Step 1 — Install Java 21**

Download from [adoptium.net](https://adoptium.net) → Temurin 21 LTS

Verify:
```bash
java -version
# Should show: openjdk version "21.x.x"
```

**Step 2 — Install Git**

Download from [git-scm.com](https://git-scm.com)

Verify:
```bash
git --version
```

**Step 3 — Install your IDE**

- IntelliJ IDEA: [jetbrains.com/idea](https://www.jetbrains.com/idea/) (Community = free)
- VS Code: [code.visualstudio.com](https://code.visualstudio.com) + Java + Spring extensions

> You do **NOT** need to install Gradle separately. The project has a Gradle wrapper (`gradlew`) that handles this automatically.

---

### Clone the Repository

```bash
# Navigate to where you want the project
cd ~/Desktop

# Clone it
git clone https://github.com/YOUR_USERNAME/spring-ai-demo.git

# Enter the project
cd spring-ai-demo
```

---

### Set Up Environment Variables

The project needs your OpenAI API key. Set it up on the new machine.

**Windows (PowerShell):**
```powershell
# Temporary (this session only)
$env:OPENAI_API_KEY = "sk-proj-xxxxxxxxxxxxxxxx"

# Permanent (survives restarts) — run this once
[System.Environment]::SetEnvironmentVariable("OPENAI_API_KEY", "sk-proj-xxxxxxxxxxxxxxxx", "User")
```

**Mac/Linux:**
```bash
# Add to ~/.zshrc or ~/.bashrc to make it permanent
echo 'export OPENAI_API_KEY="sk-proj-xxxxxxxxxxxxxxxx"' >> ~/.zshrc
source ~/.zshrc
```

Verify it's set:
```bash
# Mac/Linux
echo $OPENAI_API_KEY

# Windows PowerShell
echo $env:OPENAI_API_KEY
```

---

### Build the Project

No Gradle install needed — the wrapper handles everything:

```bash
# Windows
.\gradlew clean build

# Mac/Linux
./gradlew clean build
```

On first run, Gradle wrapper will:
1. Download the correct Gradle version automatically
2. Download all dependencies from Maven Central
3. Compile the code
4. Run tests
5. Package the JAR

This may take 2-5 minutes the first time (downloading). Subsequent runs are fast.

Expected output:
```
BUILD SUCCESSFUL in 24s
8 actionable tasks: 8 executed
```

---

### Run the Application

```bash
# Windows
.\gradlew bootRun

# Mac/Linux
./gradlew bootRun
```

Wait for:
```
Started SpringAiDemoApplication in 4.3 seconds
```

Then test:
```bash
# In browser or curl
curl "http://localhost:8080/ask?message=Hello"
```

---

### Open in IDE

**IntelliJ IDEA:**
1. `File` → `Open` → select the `spring-ai-demo` folder
2. IntelliJ detects Gradle automatically → click **Trust Project**
3. Wait for Gradle sync (bottom progress bar)
4. Done — all dependencies are resolved, code is indexed

**VS Code:**
1. `File` → `Open Folder` → select `spring-ai-demo`
2. VS Code detects Java project → click **Yes** to import
3. Wait for Java extension to index
4. Done

---

### Full Setup Checklist for a New Laptop

```
□ Java 21 installed (java -version confirms)
□ Git installed (git --version confirms)
□ IDE installed and configured
□ Repo cloned (git clone ...)
□ OPENAI_API_KEY environment variable set
□ .\gradlew clean build → BUILD SUCCESSFUL
□ .\gradlew bootRun → app starts on port 8080
□ http://localhost:8080/ask → returns AI response
```

---

### What If Someone Else Clones Your Project?

They need to know what environment variables are required. Best practice is to include a `.env.example` file in the repo (this IS committed to Git, unlike `.env`):

`.env.example`:
```bash
# Copy this to .env or set these as environment variables
# Never commit actual values

OPENAI_API_KEY=your-openai-api-key-here
SPRING_PROFILE=local
DB_URL=jdbc:postgresql://localhost:5432/mydb
DB_USERNAME=postgres
DB_PASSWORD=your-db-password
```

Include in your README:
```markdown
## Setup
1. Clone the repo
2. Copy `.env.example` → `.env` and fill in your values
3. Run `.\gradlew bootRun`
```

This way, any developer who clones your repo immediately knows exactly what they need to configure — no guessing, no "why isn't it working" messages.

---

## Quick Reference — Most Used Commands

| Command | What it does |
|---|---|
| `.\gradlew clean build` | Full clean build + tests |
| `.\gradlew bootRun` | Run the Spring Boot app |
| `.\gradlew test` | Run tests only |
| `.\gradlew build -x test` | Build but skip tests |
| `.\gradlew dependencies` | Print full dependency tree |
| `.\gradlew tasks` | List all available tasks |
| `.\gradlew --refresh-dependencies` | Force re-download all dependencies |
| `git clone <url>` | Clone a repo |
| `git pull` | Pull latest changes |
| `git status` | See what files changed |
| `git add . && git commit -m "msg"` | Stage and commit |
| `git push` | Push to GitHub |

---

## Useful Links

| Resource | URL |
|---|---|
| Spring Initializr | [start.spring.io](https://start.spring.io) |
| Maven Central Search | [search.maven.org](https://search.maven.org) |
| MVN Repository (human friendly) | [mvnrepository.com](https://mvnrepository.com) |
| Spring Boot Docs | [docs.spring.io/spring-boot](https://docs.spring.io/spring-boot/docs/current/reference/html/) |
| Spring AI Docs | [docs.spring.io/spring-ai](https://docs.spring.io/spring-ai/reference/) |
| Adoptium JDK Downloads | [adoptium.net](https://adoptium.net) |
| Gradle Docs | [docs.gradle.org](https://docs.gradle.org) |
| IntelliJ IDEA | [jetbrains.com/idea](https://www.jetbrains.com/idea/) |
