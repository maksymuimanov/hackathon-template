# Backend Architecture

## Technology Stack

- **Framework:** Spring Boot 3.x
- **Language:** Kotlin
- **Java Version:** 25
- **Build Tool:** Gradle (Kotlin DSL)
- **Code Formatting:** Spotless with ktlint
- **Testing:** JUnit 5 with Kotlin Test

## Architecture Decisions

### ADR-001: In-Memory Data Persistence

**Status:** Accepted

**Context:**
This is a hackathon template requiring rapid development and deployment. Setting up and maintaining a database adds complexity, configuration overhead, and deployment challenges.

**Decision:**
Use in-memory data persistence exclusively. No database, JPA, or SQL. All data is stored in memory using `ConcurrentHashMap` or custom repository implementations.

**Consequences:**
- **Positive:** Simplified setup, no database configuration, faster development, easier deployment
- **Negative:** Data lost on application restart, not suitable for production data persistence
- **Mitigation:** Acceptable for hackathon/demo purposes; can be upgraded to database later if needed

### ADR-002: InMemoryMapRepository Pattern

**Status:** Accepted

**Context:**
Need a consistent pattern for in-memory data repositories that provides CRUD operations and query capabilities without database dependencies.

**Decision:**
Create abstract `InMemoryMapRepository<K, V>` base class using `ConcurrentHashMap` for thread-safe in-memory storage. Provides standard CRUD methods: `save`, `findById`, `findAll`, `deleteById`, `deleteBy`, etc. Uses reflection to extract ID from entities via `@Id` annotation or field named "id".

**Consequences:**
- **Positive:** Consistent repository pattern, thread-safe operations, rich query API, type-safe
- **Negative:** Reflection-based ID extraction has performance overhead, limited to single-field IDs
- **Location:** `squad.nononotaa.backend.repository.InMemoryMapRepository`

### ADR-003: Package Structure

**Status:** Accepted

**Context:**
Need clear organization for Kotlin code following Spring Boot best practices.

**Decision:**
Organize by feature/domain under `squad.nononotaa.backend`:
- `config/` - Configuration classes
- `controller/` - REST controllers
- `dto/` - Data transfer objects
- `exception/` - Custom exceptions and error handling
- `model/` - Domain models
- `repository/` - Repository implementations
- `service/` - Business logic services
- `util/` - Utility classes

**Consequences:**
- **Positive:** Clear separation of concerns, follows Spring Boot conventions, easy to navigate
- **Negative:** None significant

### ADR-004: Exception Handling

**Status:** Accepted

**Context:**
Need consistent error responses across REST API endpoints.

**Decision:**
Use `@RestControllerAdvice` with `RestExceptionHandler` class to handle exceptions globally:
- `NotFoundException` → 404 NOT_FOUND
- `ValidationException` → 422 UNPROCESSABLE_CONTENT
- `Exception` → 500 INTERNAL_SERVER_ERROR

**Consequences:**
- **Positive:** Consistent error responses, centralized error handling, clean controller code
- **Negative:** None significant
- **Location:** `squad.nononotaa.backend.exception.RestExceptionHandler`

### ADR-005: ApplicationInitJob for Data Initialization

**Status:** Accepted

**Context:**
Since the application uses in-memory persistence, all data is lost on restart. Need a mechanism to populate initial data on application startup for demo/hackathon purposes.

**Decision:**
Create `ApplicationInitJob` interface that components can implement to execute initialization logic on startup. `ApplicationConfig` automatically discovers and executes all `ApplicationInitJob` beans via `ApplicationRunner`. Each job is logged and executed sequentially.

**Consequences:**
- **Positive:** Automatic data population on startup, extensible pattern for multiple init jobs, clear separation of initialization logic
- **Negative:** Data still lost on restart, initialization runs on every startup
- **Location:** `squad.nononotaa.backend.config.ApplicationInitJob`, `squad.nononotaa.backend.config.ApplicationConfig`
- **Usage:** Create a `@Component` class implementing `ApplicationInitJob` to populate repositories with initial data

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── kotlin/squad/nononotaa/backend/
│   │   │   ├── BackendApplication.kt
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── util/
│   │   └── resources/
│   │       ├── application.yaml
│   │       ├── application-dev.yaml
│   │       ├── application-local.yaml
│   │       ├── application-prod.yaml
│   │       ├── static/
│   │       └── templates/
│   └── test/kotlin/
├── build.gradle.kts
└── gradlew
```

## Key Components

### InMemoryMapRepository
Abstract base class for in-memory repositories with thread-safe CRUD operations. Uses `ConcurrentHashMap` for storage and reflection for ID extraction.

### ApplicationInitJob
Interface for data initialization on application startup. Implement as `@Component` to populate in-memory repositories automatically.

### Error Handling
- **NotFoundException** - Custom exception for 404 errors
- **ValidationException** - Custom exception for validation failures (422)
- **ErrorResponse** - Consistent error response data class with HTTP status and message
- **RestExceptionHandler** - Global `@RestControllerAdvice` that converts exceptions to HTTP error responses

### Logger Extension
Extension function `T.logger()` in `util/Extensions.kt` provides idiomatic Kotlin logger creation:
```kotlin
companion object {
    private val logger = logger()
}
```

### Configuration
- `application.yaml` - Base configuration with profile-based application naming
- Profile-specific configs: `dev`, `local`, `prod`

## Development Guidelines

- Use primary constructors for dependency injection
- Prefer `val` over `var` for immutability
- Use `data class` for DTOs
- Use companion objects for loggers
- Run `./gradlew spotlessApply` before committing
- NO JPA, NO database dependencies
- Use in-memory repositories extending `InMemoryMapRepository`
