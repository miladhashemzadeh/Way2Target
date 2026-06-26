# Custom Rules

- **Never commit changes**: Do not run `git commit` commands or commit files. Let the user review and commit changes.
- **Always use RTK**: Always prefix terminal commands with `rtk` (e.g., `rtk git status`, `rtk git diff`, etc.) for token optimization.

# Modular Clean Architecture Guidelines

To maintain absolute separation of concerns and prevent build cycles, all agents must adhere to the following architectural rules:

### 1. Module Dependency Rules (strict DAG)
- **`:domain` (Core Business Logic)**
  - Core of the app containing only business entities, use cases, and repository interfaces.
  - **Constraints**: Must remain a pure Kotlin module. It **must never** depend on any other local project module (no dependencies on `:data`, `:core`, `:sharedUI`, etc.).
- **`:sharedUI` (Reusable Design System & Localizations)**
  - Contains reusable design tokens, common composables, catalog layouts, themes, and `AppStrings`/localizations.
  - **Constraints**: It **must never** depend on `:domain`, `:core`, `:di`, or any feature modules (`*FT`). It must remain a lightweight UI presentation library.
- **`:data` (Data Infrastructure)**
  - Houses network APIs, local database (SQLDelight), cache layers, and repository implementations.
  - **Constraints**: Depends on `:domain` to implement its repository contracts.
- **Feature Modules (`*FT` e.g., `SHomeFT`, `TargetFT`, `SChallengeFT`)**
  - Modular features containing MVI components, screens, and localized view-models.
  - **Constraints**: Can depend on `:sharedUI` for styling, `:domain` for logic, `:core` for helpers, and `:di` for instantiation. They **must never** depend on other feature modules directly.
- **`:core` (Common Utilities)**
  - Utility and extension functions. Depends on `:sharedUI`.
- **`:di` (Dependency Injection Configuration)**
  - Sets up Koin modules across all repository, usecase, and feature boundaries.
  - **Constraints**: Depends on `:domain`, `:data`, `:core`, and feature modules.
- **`:composeApp` (System Host Shell)**
  - Combines all modules together.

### 2. Cleanup Constraint
- When refactoring code or transitioning layouts (e.g., deleting duplicated headers or obsolete screens), always identify and recursively delete unused files, unused packages, and imports immediately. Never leave dead code in the project.
