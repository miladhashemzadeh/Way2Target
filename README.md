# 🎯 Way2Target

Way2Target is a modern, cross-platform self-growth lifestyle application designed to empower individuals to achieve their personal goals and navigate life's challenges with confidence.

---

## ✨ Features

- 🎯 **Goal Selection** – Define your path to success by choosing and customizing personal goals.
- 📈 **Progress Guidance** – Actionable, data-driven steps to keep you moving forward.
- 🧠 **Challenge Support** – AI-driven or strategy-based assistance when facing obstacles.
- 🛠️ **Practical Strategies** – A library of effective methods to overcome difficulties.
- 🌓 **Modern UI** – Beautifully designed interface with dark/light mode support.

---

## 🏗️ Architecture

The project follows a **Modular Clean Architecture** approach, ensuring scalability and maintainability.

- **MVI (Model-View-Intent)**: Ensures a predictable state management flow across the UI.
- **Decompose**: Used for lifecycle-aware component navigation and business logic encapsulation.
- **Dependency Injection**: Powered by **Koin** for clean and modular dependency management.
- **Clean Architecture**: Separation of concerns into `domain`, `data`, `core`, and feature-specific modules (`*FT`).

---

## 🛠️ Tech Stack

- **Multiplatform**: [Kotlin Multiplatform (KMP)](https://kotlinlang.org/docs/multiplatform.html)
- **UI Framework**: [Jetpack Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- **Navigation & Lifecycle**: [Decompose](https://github.com/arkivanov/Decompose)
- **Dependency Injection**: [Koin](https://insert-koin.io/)
- **Reactive Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Local Database**: [SQLDelight](https://cashapp.github.io/sqldelight/) (planned/in-use)
- **Resources**: [Compose Resources](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-resources.html)

---

## 📂 Project Structure

The project is highly modularized to keep feature logic isolated:

```text
├── composeApp/          # Main entry point for Android and iOS
├── core/               # Shared utilities, base classes, and extensions
├── domain/             # Business logic and entity definitions
├── data/               # Data sources, repositories, and API/DB implementations
├── di/                 # Dependency injection modules
├── sharedUI/           # Common UI components, themes, and design system
└── *FT/                # Feature-specific modules (e.g., TargetFT, MoodAddFT, etc.)
    ├── SHomeFT         # Home Feature
    ├── TargetFT        # Goal/Target Management
    ├── SolutionFT      # Solution Discovery
    ├── PrefrencesFT    # User Settings
    └── ...             # And more
```

---

## 🚀 Getting Started

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/vampyreLord/Way2Target.git
    ```
2.  **Open in Android Studio**:
    Ensure you have the latest **Kotlin Multiplatform** plugin installed.
3.  **Run the app**:
    -   Select `composeApp` and run on an **Android Emulator** or **iOS Simulator**.

---

## 📌 Purpose

**Way2Target** is more than just a tracker; it's a companion for continuous growth. Built with the latest Android & Multiplatform standards, it provides a seamless experience across devices.

> *Build your path. Achieve your goals. Become your best self.*
