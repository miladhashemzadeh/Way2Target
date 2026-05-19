package com.vampyreworld.w2t.onboarding

interface OnboardingContract {
    data class State(
        val currentPage: Int = 0
    )

    sealed interface SideEffect {
        data object Finished : SideEffect
    }

    sealed interface Intent {
        data object OnFinishClicked : Intent
        data class OnPageChanged(val page: Int) : Intent
    }
}
