# AI Agent Guidelines for Way2Target Project

## Architecture: MVI + Decompose

We use **Decompose** for lifecycle management and navigation, and **MVIKotlin** for state management.

### 1. Module Structure
Each feature module (e.g., `TargetFT`, `MoodAddFT`) should follow this internal structure:
- `com.vampyreworld.w2t.[feature].component`: Contains Decompose Component interfaces and implementations.
- `com.vampyreworld.w2t.[feature].store`: Contains MVIKotlin Store interfaces, Mappers, and Executors.
- `com.vampyreworld.w2t.[feature].ui`: Contains Compose UI functions that take a Component as a parameter.

### 2. Decompose Components
- Define an interface for each component.
- Use `Value<State>` for state exposure.
- Use `Flow<Label>` or similar for one-time events (Side Effects).
- Implement the component using `ComponentContext`.

### 3. MVIKotlin Stores
- Define `Intent`, `State`, and `Label` (for side effects).
- Use `StoreFactory` to create stores.
- Logic should reside in `Executor`.
- State updates should reside in `Reducer`.

### 4. Navigation
- Root navigation is handled by `RootComponent` in `composeApp`.
- Use Decompose `Child Stack` or `Child Slot` for navigation between components.
- Avoid using `androidx.navigation` or `ViewModel`.

### 5. Dependency Injection
- Use **Koin** for dependency injection.
- Components should be injected with their required Stores and UseCases.

### 6. Rules for AI Agents
- **No ViewModels**: Do not create or use `androidx.lifecycle.ViewModel`. Use Decompose Components instead.
- **MVI Pattern**: Always follow the Intent -> Store -> State/Label flow.
- **Surgical Edits**: When modifying files, use `replace_file_content` or `multi_replace_file_content` for minimal impact.
- **Commits**: Every meaningful task must be followed by a git commit with a descriptive message.
- **Consistency**: Maintain the existing code style and naming conventions.
