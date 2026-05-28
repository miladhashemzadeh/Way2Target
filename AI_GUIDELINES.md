# AI Agent Guidelines for Way2Target Project

## Architecture: MVI + Decompose

We use **Decompose** for lifecycle management and navigation, and **MVIKotlin** for state management.

### 1. Module Structure
Each feature module (e.g., `TargetFT`, `MoodAddFT`) should follow this internal structure:
- `com.vampyreworld.w2t.[feature]`: Root package for the feature, contains the Koin Module and Feature Contract.
- `com.vampyreworld.w2t.[feature].component`: Contains Decompose Component interfaces and implementations.
- `com.vampyreworld.w2t.[feature].store`: Contains MVIKotlin Store interfaces, Mappers, and Executors.
- `com.vampyreworld.w2t.[feature].ui`: Contains Compose UI functions that take a Component as a parameter.

### 2. Feature Contracts (State & SideEffects)
Each feature should define a `[Feature]Contract` to centralize its MVI definitions:
- **State**: A data class representing the UI state.
- **SideEffect**: A sealed interface for one-time events (e.g., navigation, toasts).
- **Intent**: A sealed interface for user actions.

Example:
```kotlin
interface HomeContract {
    data class State(val isLoading: Boolean = false)
    sealed interface SideEffect
    sealed interface Intent
}
```

### 3. Decompose Components
- Define an interface for each component.
- Expose `Value<State>` and `Flow<SideEffect>`.
- Use `onIntent(Intent)` for user actions.
- Implement the component using `ComponentContext`.

### 4. MVIKotlin Stores
- Use MVIKotlin `Store` for complex state logic.
- Maps `Intent` to `Executor` logic and `Reducer` state updates.
- Use `Label` in Store to emit `SideEffect` from the Component.

### 5. Modular Dependency Injection
- Each feature must have its own Koin module (e.g., `targetModule`).
- Dependencies (UseCases, Stores) must be defined within the feature's Koin module.
- Features are dynamically installed in the main app via `includes()` in `AppModules.kt`.

### 6. Rules for AI Agents
- **No ViewModels**: Do not create or use `androidx.lifecycle.ViewModel`. Use Decompose Components instead.
- **MVI Pattern**: Always follow the Intent -> Store -> State/Label flow.
- **Surgical Edits**: When modifying files, use `replace_file_content` or `multi_replace_file_content` for minimal impact.
- **Commits**: Every meaningful task must be followed by a git commit with a descriptive message.
- **Consistency**: Maintain the existing code style and naming conventions.

### 7. RTK Rules

When running terminal commands in this project:

- Prefer `.\rtk` wrappers over raw shell commands whenever possible.
- Use `.\rtk git status` instead of `git status`
- Use `.\rtk read <file>` instead of `cat <file>`
- Use `.\rtk grep <pattern>` instead of `grep`
- Use `.\rtk find` instead of `find`
- Use `.\rtk test` for tests
- Use `.\rtk err <command>` when checking build errors
- Use `.\rtk diff` for diffs
- Prefer compact output to minimize token usage

Goal:
Reduce terminal output size and token usage while keeping relevant debugging information.
