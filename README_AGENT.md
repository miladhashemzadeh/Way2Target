# Agent Instructions for Way2Target

Welcome, agent. This project is a Kotlin Multiplatform (KMP) application named **Way2Target**.

## Environment Setup
Run the initialization script to set up useful terminal aliases and check your environment:
```bash
source scripts/init_dev.sh
```

## Project Rules
1.  **Architecture**: MVI (using MVIKotlin) + Decompose for navigation.
2.  **Database**: SQLDelight for local storage.
3.  **UI**: Jetpack Compose Multiplatform.
4.  **Serialization**: Kotlinx Serialization is required for custom types in the database.
5.  **Repository Logic**: Ensure that relationships between Master, Milestone, and Action goals are maintained during Save/Delete operations.
6.  **Style**: Keep the UI consistent with `W2TCatalog.kt` and use `LocalAppColorScheme.current`.

## Useful Commands
-   `./gradlew sync` - Sync Gradle.
-   `./gradlew :composeApp:assembleDebug` - Build Android App.
-   `./gradlew :data:generateCommonMainW2TDatabaseInterface` - Generate SQLDelight interfaces.
