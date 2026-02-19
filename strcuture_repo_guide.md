# 🏗️ Java Code Structure & SOLID Principles
### How a Principal Engineer Writes Java from Scratch

Everything you need to know about structuring production-grade Java code — the mindset, the patterns, the folder structure, and the SOLID principles that separate junior code from principal-level code.

---

## Table of Contents

1. [The Mindset — How Principal Engineers Think](#1-the-mindset--how-principal-engineers-think)
2. [SOLID Principles — In Depth with Java Examples](#2-solid-principles--in-depth-with-java-examples)
3. [Production-Grade Project Structure](#3-production-grade-project-structure)
4. [Layered Architecture — What Goes Where](#4-layered-architecture--what-goes-where)
5. [Package by Feature, Not by Layer](#5-package-by-feature-not-by-layer)
6. [Design Patterns Principal Engineers Use](#6-design-patterns-principal-engineers-use)
7. [Full Example — Spring AI Demo Restructured](#7-full-example--spring-ai-demo-restructured)
8. [Code Quality Rules](#8-code-quality-rules)
9. [Common Anti-Patterns to Avoid](#9-common-anti-patterns-to-avoid)

---

## 1. The Mindset — How Principal Engineers Think

Before writing a single line of code, a principal engineer asks:

```
"Will the next developer (or me in 6 months) understand this instantly?"
"Can I change one thing without breaking five others?"
"Is each class doing exactly ONE job?"
"Can I test this without spinning up the entire application?"
```

The difference between junior and principal code is not cleverness — it's **clarity, maintainability, and discipline**.

### The Three Laws of Clean Code

**Law 1 — Code is written once, read a hundred times.**
Optimize for the reader, not the writer. If something needs a comment to explain what it does, the code itself is not clear enough.

**Law 2 — Change is inevitable.**
Requirements will change. External APIs will change. Databases will change. Write code that can absorb change in one place without rippling across the entire codebase.

**Law 3 — Each unit of code has one reason to exist.**
A class that sends emails AND validates input AND logs errors is a class that will cause you problems. Split responsibilities ruthlessly.

---

## 2. SOLID Principles — In Depth with Java Examples

SOLID is an acronym for five design principles that make code maintainable, extensible, and testable. These are not abstract theory — they are daily practice for senior engineers.

---

### S — Single Responsibility Principle (SRP)

> **"A class should have only one reason to change."**

Every class, method, and module should do exactly ONE thing. If you can describe a class with the word "and", it's doing too much.

#### ❌ Violating SRP

```java
// This class does THREE things: business logic, email sending, AND database access
// If email provider changes → edit this class
// If DB changes → edit this class
// If chat logic changes → edit this class
// Three reasons to change = three responsibilities = violation
@Service
public class ChatService {

    @Autowired
    private EntityManager entityManager;

    public String processMessage(String userMessage) {
        // 1. Call OpenAI
        String response = callOpenAI(userMessage);

        // 2. Save to database (DB responsibility — doesn't belong here)
        entityManager.persist(new ChatMessage(userMessage, response));

        // 3. Send email notification (Email responsibility — doesn't belong here)
        sendEmail("admin@example.com", "New chat: " + userMessage);

        return response;
    }

    private void sendEmail(String to, String body) {
        // email logic...
    }

    private String callOpenAI(String message) {
        // OpenAI logic...
    }
}
```

#### ✅ Following SRP

```java
// Each class has ONE job

@Service
public class ChatService {
    private final AiClient aiClient;
    private final ChatRepository chatRepository;
    private final NotificationService notificationService;

    public ChatService(AiClient aiClient,
                       ChatRepository chatRepository,
                       NotificationService notificationService) {
        this.aiClient = aiClient;
        this.chatRepository = chatRepository;
        this.notificationService = notificationService;
    }

    public ChatResponse processMessage(String userMessage) {
        // Only orchestrates — delegates the actual work
        String aiResponse = aiClient.ask(userMessage);
        chatRepository.save(new ChatMessage(userMessage, aiResponse));
        notificationService.notifyAdmin(userMessage);
        return new ChatResponse(aiResponse);
    }
}

// Separate class for AI — its only job is talking to OpenAI
@Component
public class AiClient {
    private final ChatClient chatClient;

    public AiClient(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String ask(String message) {
        return chatClient.prompt().user(message).call().content();
    }
}

// Separate class for notifications — its only job is sending emails
@Service
public class NotificationService {
    private final JavaMailSender mailSender;

    public void notifyAdmin(String message) {
        // send email logic
    }
}
```

---

### O — Open/Closed Principle (OCP)

> **"Software should be open for extension, closed for modification."**

You should be able to add new behavior WITHOUT editing existing, tested code. Add new classes — don't modify old ones.

#### ❌ Violating OCP

```java
// Every time you add a new AI provider, you must edit this class
// Editing tested code is risky — you can break what already works
@Service
public class AiService {

    public String getResponse(String provider, String message) {
        if (provider.equals("openai")) {
            // OpenAI logic
            return callOpenAI(message);
        } else if (provider.equals("gemini")) {
            // Gemini logic
            return callGemini(message);
        } else if (provider.equals("claude")) {
            // Claude logic — you had to edit this class to add this
            return callClaude(message);
        }
        throw new IllegalArgumentException("Unknown provider: " + provider);
    }
}
```

#### ✅ Following OCP

```java
// Define the contract (interface)
public interface AiProvider {
    String ask(String message);
    String getProviderName();
}

// Add new providers by creating new classes — never touch existing ones
@Component
public class OpenAiProvider implements AiProvider {
    @Override
    public String ask(String message) {
        // OpenAI specific logic
        return "OpenAI response";
    }

    @Override
    public String getProviderName() { return "openai"; }
}

@Component
public class GeminiProvider implements AiProvider {
    @Override
    public String ask(String message) {
        // Gemini specific logic
        return "Gemini response";
    }

    @Override
    public String getProviderName() { return "gemini"; }
}

// Service is closed for modification — it never changes when you add providers
@Service
public class AiService {
    private final Map<String, AiProvider> providers;

    // Spring injects ALL AiProvider implementations automatically
    public AiService(List<AiProvider> providerList) {
        this.providers = providerList.stream()
            .collect(Collectors.toMap(AiProvider::getProviderName, p -> p));
    }

    public String getResponse(String providerName, String message) {
        AiProvider provider = providers.get(providerName);
        if (provider == null) throw new IllegalArgumentException("Unknown provider: " + providerName);
        return provider.ask(message);
    }
}

// Adding a new provider? Just create a new class. Done. Nothing else changes.
@Component
public class ClaudeProvider implements AiProvider {
    @Override
    public String ask(String message) { return "Claude response"; }

    @Override
    public String getProviderName() { return "claude"; }
}
```

---

### L — Liskov Substitution Principle (LSP)

> **"Objects of a subclass should be replaceable with objects of the parent class without breaking the application."**

If class B extends class A, anywhere you use A, you should be able to plug in B and everything still works correctly. Violations happen when subclasses override behavior in ways that break parent class contracts.

#### ❌ Violating LSP

```java
public class Bird {
    public void fly() {
        System.out.println("Flying...");
    }
}

// Penguin IS-A Bird but cannot fly
// Substituting Bird with Penguin breaks the contract — throws exception
public class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}

// This code breaks when a Penguin is passed in
public void makeBirdFly(Bird bird) {
    bird.fly(); // Explodes if bird is a Penguin
}
```

#### ✅ Following LSP

```java
// Separate the concept of "bird" from "flying bird"
public abstract class Bird {
    public abstract void eat();
    public abstract void sleep();
}

public interface Flyable {
    void fly();
}

// Flying birds implement both
public class Eagle extends Bird implements Flyable {
    @Override public void eat() { System.out.println("Eagle eating"); }
    @Override public void sleep() { System.out.println("Eagle sleeping"); }
    @Override public void fly() { System.out.println("Eagle flying"); }
}

// Penguins extend Bird but don't implement Flyable — honest contract
public class Penguin extends Bird {
    @Override public void eat() { System.out.println("Penguin eating"); }
    @Override public void sleep() { System.out.println("Penguin sleeping"); }
    // No fly() — penguins don't fly, and we don't pretend they do
}

// Now this is safe — only accepts things that can actually fly
public void makeFlyableObjectFly(Flyable flyable) {
    flyable.fly(); // Always safe, contract is honest
}
```

#### Real-World Spring Example

```java
// Base repository contract
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByUserId(String userId);
}

// Any subclass/implementation must honor the contract fully
// If you swap PostgreSQL for MongoDB, both implementations must
// return the same results for the same inputs — LSP in practice
@Repository
public class PostgresChatMessageRepository implements MessageRepository {
    // full implementation
}
```

---

### I — Interface Segregation Principle (ISP)

> **"Clients should not be forced to depend on interfaces they do not use."**

Don't create fat interfaces with 15 methods when a class only needs 2. Split interfaces into small, focused ones.

#### ❌ Violating ISP

```java
// One giant interface forces ALL implementers to implement ALL methods
public interface UserOperations {
    User findById(String id);
    void createUser(User user);
    void deleteUser(String id);
    void sendWelcomeEmail(User user);      // Email — not all services need this
    byte[] exportUserDataToCsv(String id); // CSV export — not all services need this
    void generateUserReport(String id);    // Reporting — not all services need this
}

// This class only needs CRUD but is forced to implement email, CSV, reports
@Service
public class BasicUserService implements UserOperations {
    @Override public User findById(String id) { /* ... */ return null; }
    @Override public void createUser(User user) { /* ... */ }
    @Override public void deleteUser(String id) { /* ... */ }

    // Forced to implement these even though this service doesn't need them
    @Override public void sendWelcomeEmail(User user) {
        throw new UnsupportedOperationException("Not supported");
    }
    @Override public byte[] exportUserDataToCsv(String id) {
        throw new UnsupportedOperationException("Not supported");
    }
    @Override public void generateUserReport(String id) {
        throw new UnsupportedOperationException("Not supported");
    }
}
```

#### ✅ Following ISP

```java
// Small, focused interfaces — each represents one capability

public interface UserCrudOperations {
    User findById(String id);
    void createUser(User user);
    void deleteUser(String id);
}

public interface UserNotificationOperations {
    void sendWelcomeEmail(User user);
    void sendPasswordResetEmail(User user);
}

public interface UserExportOperations {
    byte[] exportToCsv(String userId);
    void generateReport(String userId);
}

// Services implement only what they actually need
@Service
public class UserService implements UserCrudOperations {
    // Only implements what it uses — clean, honest contract
    @Override public User findById(String id) { /* ... */ return null; }
    @Override public void createUser(User user) { /* ... */ }
    @Override public void deleteUser(String id) { /* ... */ }
}

@Service
public class UserNotificationService implements UserNotificationOperations {
    @Override public void sendWelcomeEmail(User user) { /* ... */ }
    @Override public void sendPasswordResetEmail(User user) { /* ... */ }
}

// A full-featured admin service can implement multiple interfaces
@Service
public class AdminUserService implements UserCrudOperations,
                                         UserNotificationOperations,
                                         UserExportOperations {
    // Implements everything — but only because it genuinely needs all of it
}
```

---

### D — Dependency Inversion Principle (DIP)

> **"High-level modules should not depend on low-level modules. Both should depend on abstractions."**

Your business logic (high-level) should not know or care whether you're using PostgreSQL, MongoDB, or an in-memory store (low-level). It should only talk to an interface. This is the foundation of testability.

#### ❌ Violating DIP

```java
// ChatService is directly coupled to a specific implementation
// To test ChatService, you MUST have a real PostgreSQL running
// To swap databases, you must rewrite ChatService
@Service
public class ChatService {

    // Direct dependency on the concrete class — tightly coupled
    private final PostgresChatRepository repository = new PostgresChatRepository();

    public ChatResponse processMessage(String message) {
        String response = "AI response";
        // Directly uses concrete implementation
        repository.saveToPostgres(new ChatMessage(message, response));
        return new ChatResponse(response);
    }
}
```

#### ✅ Following DIP

```java
// Define an abstraction (interface) — the contract
public interface ChatRepository {
    void save(ChatMessage message);
    List<ChatMessage> findByUserId(String userId);
}

// Low-level module: PostgreSQL implementation
@Repository
public class PostgresChatRepository implements ChatRepository {
    private final JpaRepository<ChatMessage, Long> jpaRepo;

    @Override
    public void save(ChatMessage message) {
        jpaRepo.save(message);
    }

    @Override
    public List<ChatMessage> findByUserId(String userId) {
        return jpaRepo.findByUserId(userId);
    }
}

// Low-level module: In-memory implementation (for tests)
public class InMemoryChatRepository implements ChatRepository {
    private final List<ChatMessage> store = new ArrayList<>();

    @Override
    public void save(ChatMessage message) {
        store.add(message);
    }

    @Override
    public List<ChatMessage> findByUserId(String userId) {
        return store.stream()
            .filter(m -> m.getUserId().equals(userId))
            .collect(Collectors.toList());
    }
}

// High-level module: depends on the INTERFACE, not the implementation
// Spring injects the right implementation at runtime
@Service
public class ChatService {

    private final ChatRepository chatRepository; // Interface — not a concrete class
    private final AiClient aiClient;

    // Constructor injection — the correct way in Spring
    public ChatService(ChatRepository chatRepository, AiClient aiClient) {
        this.chatRepository = chatRepository;
        this.aiClient = aiClient;
    }

    public ChatResponse processMessage(String userId, String message) {
        String aiResponse = aiClient.ask(message);
        chatRepository.save(new ChatMessage(userId, message, aiResponse));
        return new ChatResponse(aiResponse);
    }
}

// In tests — swap to InMemory without touching ChatService at all
@SpringBootTest
class ChatServiceTest {
    @MockBean
    private ChatRepository chatRepository; // Mock injected, no DB needed

    @Autowired
    private ChatService chatService;

    @Test
    void shouldReturnAiResponse() {
        when(chatRepository.findByUserId("user1")).thenReturn(List.of());
        // Test without any real database
    }
}
```

---

## 3. Production-Grade Project Structure

Here is the full folder structure a principal engineer uses for a Spring Boot project:

```
spring-ai-demo/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/springaidemo/
│   │   │       │
│   │   │       ├── SpringAiDemoApplication.java       ← Entry point only
│   │   │       │
│   │   │       ├── chat/                              ← Feature package
│   │   │       │   ├── ChatController.java            ← HTTP layer
│   │   │       │   ├── ChatService.java               ← Business logic
│   │   │       │   ├── ChatRepository.java            ← Data interface
│   │   │       │   ├── ChatMessage.java               ← Domain entity
│   │   │       │   ├── ChatRequest.java               ← Input DTO
│   │   │       │   └── ChatResponse.java              ← Output DTO
│   │   │       │
│   │   │       ├── ai/                                ← AI integration feature
│   │   │       │   ├── AiClient.java                  ← Wraps Spring AI
│   │   │       │   ├── AiProvider.java                ← Interface (OCP)
│   │   │       │   ├── OpenAiProvider.java            ← Implementation
│   │   │       │   └── AiConfig.java                  ← AI bean config
│   │   │       │
│   │   │       ├── user/                              ← User feature
│   │   │       │   ├── UserController.java
│   │   │       │   ├── UserService.java
│   │   │       │   ├── UserRepository.java
│   │   │       │   ├── User.java
│   │   │       │   ├── UserRequest.java
│   │   │       │   └── UserResponse.java
│   │   │       │
│   │   │       └── common/                            ← Shared infrastructure
│   │   │           ├── exception/
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           │   ├── ResourceNotFoundException.java
│   │   │           │   └── ValidationException.java
│   │   │           ├── config/
│   │   │           │   ├── SecurityConfig.java
│   │   │           │   └── WebConfig.java
│   │   │           ├── dto/
│   │   │           │   └── ApiResponse.java           ← Standard API wrapper
│   │   │           └── util/
│   │   │               └── DateUtils.java
│   │   │
│   │   └── resources/
│   │       ├── application.yaml                       ← Shared config
│   │       ├── application-local.yaml                 ← Local dev (gitignored)
│   │       ├── application-dev.yaml                   ← Dev environment
│   │       ├── application-prod.yaml                  ← Production
│   │       └── db/migration/                          ← Flyway SQL migrations
│   │           ├── V1__create_chat_messages.sql
│   │           └── V2__create_users.sql
│   │
│   └── test/
│       └── java/
│           └── com/example/springaidemo/
│               ├── chat/
│               │   ├── ChatControllerTest.java        ← HTTP layer tests
│               │   ├── ChatServiceTest.java           ← Unit tests
│               │   └── ChatRepositoryTest.java        ← DB integration tests
│               ├── ai/
│               │   └── AiClientTest.java
│               └── common/
│                   └── GlobalExceptionHandlerTest.java
│
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
├── Dockerfile
├── docker-compose.yml
├── .gitignore
├── .env.example
└── README.md
```

---

## 4. Layered Architecture — What Goes Where

Every layer has a strict responsibility. Principal engineers never mix these.

```
┌─────────────────────────────────────────┐
│           HTTP / Controller Layer        │  ← Handles HTTP, calls Service
│         ChatController.java             │     Never contains business logic
└─────────────────┬───────────────────────┘
                  │ calls
┌─────────────────▼───────────────────────┐
│            Service Layer                 │  ← ALL business logic lives here
│           ChatService.java              │     Orchestrates, makes decisions
└─────────────────┬───────────────────────┘
                  │ calls
┌─────────────────▼───────────────────────┐
│           Repository Layer               │  ← Data access only
│          ChatRepository.java            │     No business logic, no HTTP
└─────────────────┬───────────────────────┘
                  │ maps to
┌─────────────────▼───────────────────────┐
│             Domain / Entity              │  ← Pure data model
│            ChatMessage.java             │     Reflects database table
└─────────────────────────────────────────┘
```

### The Controller — Only HTTP Concerns

```java
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor  // Lombok — generates constructor for final fields
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ApiResponse<ChatResponse>> sendMessage(
            @Valid @RequestBody ChatRequest request) {

        // Controller does: validate input, call service, return HTTP response
        // Controller does NOT: contain if/else business logic, access DB, call AI directly
        ChatResponse response = chatService.processMessage(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<ApiResponse<List<ChatResponse>>> getHistory(
            @PathVariable String userId) {

        List<ChatResponse> history = chatService.getChatHistory(userId);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
}
```

### The Service — Only Business Logic

```java
@Service
@RequiredArgsConstructor
@Slf4j  // Lombok — gives you log.info(), log.error() etc
public class ChatService {

    private final AiClient aiClient;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;  // maps entity ↔ DTO

    public ChatResponse processMessage(ChatRequest request) {
        log.info("Processing message for user: {}", request.getUserId());

        // Business logic lives here
        String aiResponse = aiClient.ask(request.getMessage());

        ChatMessage message = chatMapper.toEntity(request, aiResponse);
        chatRepository.save(message);

        log.info("Message processed successfully for user: {}", request.getUserId());
        return chatMapper.toResponse(message);
    }

    public List<ChatResponse> getChatHistory(String userId) {
        return chatRepository.findByUserId(userId)
                .stream()
                .map(chatMapper::toResponse)
                .collect(Collectors.toList());
    }
}
```

### The DTOs — Never Expose Entities Directly

```java
// Input DTO — what the client sends
@Data  // Lombok — generates getters, setters, equals, hashCode, toString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Message cannot be empty")
    @Size(max = 1000, message = "Message cannot exceed 1000 characters")
    private String message;
}

// Output DTO — what you send back to the client
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String messageId;
    private String userMessage;
    private String aiResponse;
    private LocalDateTime timestamp;
}

// Standard API wrapper — consistent response structure
@Data
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
```

### The Entity — Maps to Database

```java
@Entity
@Table(name = "chat_messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_message", nullable = false, columnDefinition = "TEXT")
    private String userMessage;

    @Column(name = "ai_response", nullable = false, columnDefinition = "TEXT")
    private String aiResponse;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
```

---

## 5. Package by Feature, Not by Layer

This is one of the biggest differences between junior and senior Java code.

### ❌ Layer-Based (Junior Approach)

```
com.example/
├── controllers/
│   ├── ChatController.java
│   ├── UserController.java
│   └── OrderController.java
├── services/
│   ├── ChatService.java
│   ├── UserService.java
│   └── OrderService.java
├── repositories/
│   ├── ChatRepository.java
│   ├── UserRepository.java
│   └── OrderRepository.java
└── models/
    ├── ChatMessage.java
    ├── User.java
    └── Order.java
```

**Problems:** To work on the chat feature, you jump between 4 different folders. Related code is scattered. As the project grows to 50+ classes, this becomes a maze.

### ✅ Feature-Based (Principal Approach)

```
com.example/
├── chat/
│   ├── ChatController.java
│   ├── ChatService.java
│   ├── ChatRepository.java
│   ├── ChatMessage.java          ← Everything chat-related in ONE place
│   ├── ChatRequest.java
│   └── ChatResponse.java
├── user/
│   ├── UserController.java
│   ├── UserService.java          ← Everything user-related in ONE place
│   ├── UserRepository.java
│   └── User.java
└── common/                       ← Only truly shared code here
    ├── ApiResponse.java
    └── GlobalExceptionHandler.java
```

**Benefits:** All chat code is in one folder. A new developer finds everything instantly. Features can be extracted to microservices easily. Teams can own features without conflicts.

---

## 6. Design Patterns Principal Engineers Use

These are the patterns you'll see in every professional Spring codebase.

### Pattern 1 — Strategy Pattern (pair with OCP)

Swap algorithms/behaviors at runtime without if/else chains.

```java
// Already shown in OCP section — AiProvider interface with multiple implementations
// Spring auto-discovers all implementations and you pick at runtime
```

### Pattern 2 — Builder Pattern (for complex objects)

```java
// Use Lombok @Builder — don't write builders manually
@Builder
public class ChatConfig {
    private final String model;
    private final int maxTokens;
    private final double temperature;
    private final boolean streamResponse;
}

// Usage — readable, no 8-arg constructor confusion
ChatConfig config = ChatConfig.builder()
        .model("gpt-4.1-mini")
        .maxTokens(1000)
        .temperature(0.7)
        .streamResponse(false)
        .build();
```

### Pattern 3 — Repository Pattern (already shown in DIP)

Abstracts data access behind an interface. Business logic never knows about SQL.

### Pattern 4 — Factory Pattern

```java
// When object creation logic is complex
public class AiProviderFactory {

    private final Map<String, AiProvider> providers;

    public AiProviderFactory(List<AiProvider> providerList) {
        this.providers = providerList.stream()
            .collect(Collectors.toMap(AiProvider::getProviderName, p -> p));
    }

    public AiProvider getProvider(String name) {
        return Optional.ofNullable(providers.get(name))
                .orElseThrow(() -> new IllegalArgumentException(
                    "No provider found: " + name));
    }
}
```

### Pattern 5 — Decorator Pattern

```java
// Add behavior to an object without changing its class
// Example: Add retry logic around AiClient calls

public interface AiClient {
    String ask(String message);
}

@Component
public class OpenAiClient implements AiClient {
    public String ask(String message) {
        // direct OpenAI call
        return "response";
    }
}

// Decorator: wraps OpenAiClient and adds retry behavior
@Primary
@Component
public class RetryableAiClient implements AiClient {

    private final OpenAiClient delegate;

    public RetryableAiClient(OpenAiClient delegate) {
        this.delegate = delegate;
    }

    @Override
    public String ask(String message) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                return delegate.ask(message);
            } catch (Exception e) {
                attempts++;
                if (attempts == 3) throw e;
            }
        }
        throw new RuntimeException("Max retries exceeded");
    }
}
```

---

## 7. Full Example — Spring AI Demo Restructured

Taking the original `spring-ai-demo` and restructuring it the right way.

### `SpringAiDemoApplication.java`

```java
// Entry point — nothing else. No beans defined here.
@SpringBootApplication
public class SpringAiDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringAiDemoApplication.class, args);
    }
}
```

### `chat/ChatController.java`

```java
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/ask")
    public ResponseEntity<ApiResponse<ChatResponse>> ask(
            @RequestParam(defaultValue = "Tell me about Spring AI") String message) {

        log.info("Received chat request: {}", message);
        ChatResponse response = chatService.processMessage(message);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
```

### `chat/ChatService.java`

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final AiClient aiClient;

    public ChatResponse processMessage(String message) {
        log.info("Processing message: {}", message);
        String aiResponse = aiClient.ask(message);
        return ChatResponse.builder()
                .userMessage(message)
                .aiResponse(aiResponse)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
```

### `ai/AiClient.java`

```java
// Interface — follows DIP
public interface AiClient {
    String ask(String message);
}
```

### `ai/SpringAiClient.java`

```java
// Implementation — follows OCP and DIP
@Component
@Slf4j
public class SpringAiClient implements AiClient {

    private final ChatClient chatClient;

    public SpringAiClient(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String ask(String message) {
        log.debug("Calling OpenAI with message: {}", message);
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
```

### `common/exception/GlobalExceptionHandler.java`

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(
            IllegalArgumentException ex) {
        log.error("Validation error: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body(ApiResponse.error("An unexpected error occurred"));
    }
}
```

---

## 8. Code Quality Rules

Rules that principal engineers enforce in every codebase:

### Rule 1 — Constructor Injection Only

```java
// ❌ Field injection — hides dependencies, makes testing hard
@Autowired
private ChatService chatService;

// ✅ Constructor injection — explicit, testable, works without Spring
private final ChatService chatService;

public ChatController(ChatService chatService) {
    this.chatService = chatService;
}

// ✅ With Lombok — @RequiredArgsConstructor generates the constructor
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;  // Lombok generates constructor
}
```

### Rule 2 — Use `final` for All Injected Fields

Signals immutability. Prevents accidental reassignment. Makes thread safety clearer.

```java
private final ChatService chatService;  // ✅ final
private ChatService chatService;        // ❌ mutable
```

### Rule 3 — Never Return `null`, Use Optional

```java
// ❌ Caller forgets to null-check → NullPointerException in production
public ChatMessage findById(String id) {
    return repository.findOne(id); // could be null
}

// ✅ Forces caller to handle the "not found" case explicitly
public Optional<ChatMessage> findById(String id) {
    return repository.findById(id);
}

// Usage
chatService.findById(id)
    .map(chatMapper::toResponse)
    .orElseThrow(() -> new ResourceNotFoundException("Chat not found: " + id));
```

### Rule 4 — Validate at the Boundary

```java
// Validate input in the Controller layer — never deep in services
@PostMapping
public ResponseEntity<?> create(@Valid @RequestBody ChatRequest request) {
    // @Valid triggers Bean Validation annotations on ChatRequest
}

// ChatRequest with validation annotations
public class ChatRequest {
    @NotBlank(message = "Message cannot be empty")
    @Size(max = 1000)
    private String message;
}
```

### Rule 5 — Use Meaningful Names

```java
// ❌ Junior code — what does this do?
public String proc(String m) {
    String r = svc.call(m);
    repo.sv(new CM(m, r));
    return r;
}

// ✅ Principal code — self-documenting
public ChatResponse processUserMessage(String userMessage) {
    String aiResponse = aiClient.ask(userMessage);
    chatRepository.save(new ChatMessage(userMessage, aiResponse));
    return new ChatResponse(aiResponse);
}
```

### Rule 6 — Log Properly

```java
@Slf4j  // Lombok — gives you `log` variable
public class ChatService {

    public ChatResponse processMessage(String message) {
        // ✅ INFO for business events
        log.info("Processing chat message for length: {}", message.length());

        try {
            String response = aiClient.ask(message);
            // ✅ DEBUG for detailed tracing (off in production)
            log.debug("AI response received, length: {}", response.length());
            return new ChatResponse(response);

        } catch (Exception e) {
            // ✅ ERROR with exception for failures
            log.error("Failed to process message: {}", e.getMessage(), e);
            throw e;
        }
    }
}
```

---

## 9. Common Anti-Patterns to Avoid

These are the code smells that principal engineers catch in code reviews:

| Anti-Pattern | Description | Fix |
|---|---|---|
| **God Class** | One class does everything — 500+ lines, 20+ methods | Split into focused classes (SRP) |
| **Anemic Domain Model** | Entities are just data bags with no behavior | Add business methods to entities |
| **Service Locator** | Using `ApplicationContext.getBean()` manually | Use constructor injection |
| **Magic Strings** | `if (type.equals("openai"))` scattered everywhere | Use enums or constants |
| **Returning null** | Methods return null instead of Optional | Use `Optional<T>` |
| **Catching Exception blindly** | `catch (Exception e) { }` swallowing errors | Catch specific exceptions, log properly |
| **Business logic in Controller** | If/else decisions in REST endpoints | Move all logic to Service layer |
| **Direct entity exposure** | Returning JPA entities from REST endpoints | Always use DTOs |
| **Static utility abuse** | Everything is a static method | Use Spring beans, enables mocking |
| **Primitive obsession** | `String userId`, `String email` everywhere | Create value objects: `UserId`, `Email` |

---

## Summary — The Principal Engineer Checklist

Before submitting any code for review, ask yourself:

```
SOLID
□ Does each class have ONE clear responsibility? (SRP)
□ Can I add new behavior without editing existing classes? (OCP)
□ Can I substitute any subclass without breaking things? (LSP)
□ Are my interfaces small and focused? (ISP)
□ Does my business logic depend on interfaces, not implementations? (DIP)

Structure
□ Is code organized by feature, not by layer?
□ Is there a clear separation between Controller, Service, Repository?
□ Am I using DTOs and never exposing entities directly?
□ Is all business logic in the Service layer?

Code Quality
□ Am I using constructor injection with final fields?
□ Am I using Optional instead of returning null?
□ Are my names meaningful and self-documenting?
□ Am I validating input at the boundary (Controller)?
□ Am I logging at the right levels?
□ Can I unit test this without spinning up Spring?
```

---

## Useful Resources

| Resource | URL |
|---|---|
| Refactoring Guru (Design Patterns) | [refactoring.guru/design-patterns](https://refactoring.guru/design-patterns) |
| Baeldung (Spring tutorials) | [baeldung.com](https://www.baeldung.com) |
| Clean Code by Robert C. Martin | Core reading for every Java engineer |
| Spring Boot Reference Docs | [docs.spring.io/spring-boot](https://docs.spring.io/spring-boot/docs/current/reference/html/) |
| Effective Java by Joshua Bloch | The bible of professional Java |
