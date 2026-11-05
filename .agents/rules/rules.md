# Agent Rules

## Architecture Constraints

- **NO DATABASE:** Never add JPA, SQL, or database dependencies. This project uses in-memory persistence only.
- **NO EXTERNAL SERVICES:** Do not add external APIs, cloud services, or third-party integrations without explicit user approval.
- **IN-MEMORY ONLY:** Use `InMemoryMapRepository` or in-memory data structures (Maps, Lists) for all data persistence.
- **DATA INITIALIZATION:** Use `ApplicationInitJob` interface to populate in-memory repositories on startup. Create `@Component` classes implementing `ApplicationInitJob` for data seeding.

## Code Quality

- **FOLLOW PATTERNS:** Use existing patterns from the codebase (package structure, dependency injection, error handling).
- **MINIMAL CHANGES:** Apply ponytail principle - smallest change that solves the problem. No over-engineering.
- **TYPE SAFETY:** Use Kotlin null-safety and TypeScript strict mode. No `!!` or `@Suppress` without justification.
- **LOGGING:** Use `logger()` extension function from `util/Extensions.kt` in companion objects:
  ```kotlin
  companion object {
      private val logger = logger()
  }
  ```
- **ERROR HANDLING:** Use custom exceptions `NotFoundException` (404) and `ValidationException` (422). `RestExceptionHandler` automatically converts these to HTTP error responses.

## Security

- **NO SECRETS:** Never hardcode API keys, passwords, or credentials. Use environment variables if needed.
- **NO DESTRUCTIVE ACTIONS:** Do not delete files, databases, or run destructive commands without explicit user confirmation.
- **NO UNSAFE DEPENDENCIES:** Do not add dependencies with security vulnerabilities or unmaintained packages.

## Workflow

- **READ FIRST:** Always read existing code and context before making changes.
- **TEST:** Write tests for new functionality. Ensure existing tests pass.
- **FORMAT:** Run formatting tools before committing (`./gradlew spotlessApply` for backend, `npm run format` for frontend).
- **DOCUMENT:** Update relevant documentation when changing architecture or adding features.

## Prohibited Actions

- Adding JPA starters or database dependencies
- Creating SQL migration files
- Adding external API clients without approval
- Hardcoding secrets or credentials
- Deleting data or files without confirmation
- Ignoring existing code patterns
- Bypassing type safety without justification
- Making unnecessary abstractions or over-engineering
