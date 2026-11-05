# {{PROJECT_NAME}}

{{PROJECT_DESCRIPTION}}

## Tech Stack

**Backend:**
- Spring Boot 3.x
- Kotlin
- Java 25
- Gradle (Kotlin DSL)
- Spotless (ktlint)
- JUnit 5

**Frontend:**
- Next.js 16 (App Router)
- React 19
- TypeScript 5
- Tailwind CSS 4
- shadcn/ui
- Turborepo

**Architecture:**
- In-memory data persistence (no database)
- RESTful API
- Monorepo with shared packages

## Quick Start

### Using Run Scripts (Recommended)

```bash
./scripts/run-backend.sh   # Run backend only
./scripts/run-frontend.sh # Run frontend only
./scripts/run-all.sh      # Run both
```

### Manual Start

**Backend:**
```bash
cd backend
./gradlew bootRun
```

Backend runs on `http://localhost:{{BACKEND_PORT}}`

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

Frontend runs on `http://localhost:{{FRONTEND_PORT}}`

## Project Structure

```
{{PROJECT_NAME}}/
├── backend/                    # Spring Boot Kotlin application
│   ├── src/
│   │   ├── main/kotlin/        # Kotlin source code
│   │   │   └── {{PACKAGE_PATH}}/
│   │   │       ├── config/     # Configuration and init jobs
│   │   │       ├── controller/ # REST controllers
│   │   │       ├── dto/        # Data transfer objects
│   │   │       ├── exception/  # Error handling
│   │   │       ├── model/      # Domain models
│   │   │       ├── repository/ # In-memory repositories
│   │   │       ├── service/    # Business logic
│   │   │       └── util/       # Utilities
│   │   └── resources/          # Configuration files
│   ├── build.gradle.kts        # Gradle build configuration
│   └── gradlew                 # Gradle wrapper
├── frontend/                   # Next.js monorepo
│   ├── apps/
│   │   └── web/               # Next.js web application
│   ├── packages/
│   │   ├── ui/                # Shared UI components (shadcn/ui)
│   │   ├── eslint-config/     # Shared ESLint configuration
│   │   └── typescript-config/ # Shared TypeScript configuration
│   ├── turbo.json             # Turborepo configuration
│   └── package.json           # Root package.json
├── scripts/
│   ├── pre-commit             # Git pre-commit hook
│   ├── run-backend.sh        # Run backend script
│   ├── run-frontend.sh      # Run frontend script
│   └── run-all.sh           # Run both services
├── .agents/
│   ├── skills/                 # Agent skills for AI assistance
│   ├── context/               # Architecture documentation and ADRs
│   └── rules/                 # Agent constraints
└── README.md
```

## Prerequisites

- **Backend:** Java 25, Gradle (included via wrapper)
- **Frontend:** Node.js >= 20.9.0, npm >= 11.13.0

## License

MIT

