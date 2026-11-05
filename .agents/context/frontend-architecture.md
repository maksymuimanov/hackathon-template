# Frontend Architecture

## Technology Stack

- **Framework:** Next.js 16 (App Router)
- **Language:** TypeScript 5
- **UI Library:** React 19
- **Styling:** Tailwind CSS 4
- **Component Library:** shadcn/ui
- **Icons:** Lucide React
- **Monorepo:** Turborepo
- **Package Manager:** npm
- **Node Version:** >= 20.9.0
- **Linting:** ESLint
- **Formatting:** Prettier with Tailwind CSS plugin

## Architecture Decisions

### ADR-001: Turborepo Monorepo

**Status:** Accepted

**Context:**
Need to manage multiple packages (web app, shared UI components, shared configs) efficiently with build caching and task orchestration.

**Decision:**
Use Turborepo for monorepo management with workspace packages structure:
- `apps/web` - Next.js web application
- `packages/ui` - Shared UI components (shadcn/ui)
- `packages/eslint-config` - Shared ESLint configuration
- `packages/typescript-config` - Shared TypeScript configuration

**Consequences:**
- **Positive:** Efficient task caching, parallel builds, shared dependencies, consistent tooling
- **Negative:** Additional tooling complexity, requires understanding of Turborepo
- **Configuration:** `turbo.json` defines task dependencies and outputs

### ADR-002: Next.js App Router

**Status:** Accepted

**Context:**
Next.js 16 uses App Router as the default routing paradigm, providing improved performance and developer experience over Pages Router.

**Decision:**
Use Next.js App Router with React Server Components by default. Use Client Components only when interactivity is needed (hooks, event handlers, browser APIs).

**Consequences:**
- **Positive:** Better performance, streaming, improved SEO, modern React patterns
- **Negative:** Learning curve for React Server Components, different mental model from Pages Router
- **Location:** `apps/web/app/` directory structure

### ADR-003: Shared UI Package

**Status:** Accepted

**Context:**
Need reusable UI components across the application with consistent styling and behavior.

**Decision:**
Create `@workspace/ui` package containing shadcn/ui components and custom components. Components are exported via package.json exports for clean imports.

**Consequences:**
- **Positive:** Component reusability, consistent design system, type-safe imports, easy maintenance
- **Negative:** Additional build step, package dependency management
- **Location:** `packages/ui/src/components/`
- **Usage:** `import { Button } from "@workspace/ui/components/button"`

### ADR-004: Tailwind CSS 4

**Status:** Accepted

**Context:**
Need modern, utility-first CSS framework with excellent developer experience and performance.

**Decision:**
Use Tailwind CSS 4 with PostCSS integration. Use Prettier plugin for automatic class sorting.

**Consequences:**
- **Positive:** Rapid UI development, consistent design, small bundle size, excellent DX
- **Negative:** Large class names in HTML, requires build step
- **Configuration:** `postcss.config.mjs` in UI package

### ADR-005: TypeScript Strict Mode

**Status:** Accepted

**Context:**
Type safety is critical for large-scale applications to catch errors at compile time.

**Decision:**
Use TypeScript strict mode across all packages. Shared TypeScript configuration in `@workspace/typescript-config` package.

**Consequences:**
- **Positive:** Type safety, better IDE support, fewer runtime errors, self-documenting code
- **Negative:** Initial setup overhead, stricter type requirements
- **Location:** `packages/typescript-config/`

### ADR-006: Dark Mode Support

**Status:** Accepted

**Context:**
Modern web applications should support dark mode for user preference and accessibility.

**Decision:**
Use `next-themes` for dark mode management with system preference detection and manual toggle capability.

**Consequences:**
- **Positive:** User preference support, accessibility, modern UX
- **Negative:** Additional dependency, requires theme provider setup
- **Location:** `packages/ui/` includes next-themes

## Project Structure

```
frontend/
├── apps/
│   └── web/
│       ├── app/
│       │   ├── layout.tsx
│       │   ├── page.tsx
│       │   └── favicon.ico
│       ├── components/
│       ├── hooks/
│       ├── lib/
│       ├── components.json
│       ├── next.config.ts
│       ├── package.json
│       └── tsconfig.json
├── packages/
│   ├── ui/
│   │   ├── src/
│   │   │   ├── components/
│   │   │   ├── hooks/
│   │   │   ├── lib/
│   │   │   └── styles/
│   │   ├── components.json
│   │   ├── package.json
│   │   └── postcss.config.mjs
│   ├── eslint-config/
│   │   ├── index.js
│   │   └── package.json
│   └── typescript-config/
│       ├── base.json
│       ├── nextjs.json
│       └── package.json
├── turbo.json
└── package.json
```

## Key Components

### Turborepo Configuration
Defines task orchestration with dependencies:
- `build` - Depends on upstream builds, outputs to `.next/`
- `lint` - Depends on upstream lint
- `format` - Depends on upstream format
- `typecheck` - Depends on upstream typecheck
- `dev` - No cache, persistent process

### Shared UI Package
Exports components, hooks, lib utilities, and global styles via package.json exports:
- `./globals.css` - Global styles
- `./components/*` - UI components
- `./hooks/*` - Custom hooks
- `./lib/*` - Utility functions

### Workspace Configurations
- **ESLint:** Shared configuration in `@workspace/eslint-config`
- **TypeScript:** Shared configurations (base, nextjs) in `@workspace/typescript-config`

## Development Guidelines

- Use functional components with hooks
- Use TypeScript for all files
- Use Server Components by default, Client Components when needed
- Import shared components from `@workspace/ui/components/*`
- Use Lucide React for icons
- Use Tailwind CSS for styling
- Run `npm run dev` from `frontend/` root to leverage Turbo
- Shared components go in `packages/ui/src/components/`
- App-specific components go in `apps/web/components/`
- Follow shadcn/ui patterns for component structure
