package com.vampyreworld.w2t.splash

interface SplashContract {
    data class State(
        val isLoading: Boolean = true
    )

    sealed interface SideEffect {
        data object NavigateNext : SideEffect
    }

    sealed interface Intent {
        data object OnFinished : Intent
    }
}
