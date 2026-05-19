package com.vampyreworld.w2t.targetft

import com.vampyreworld.w2t.domain.data.model.Goal

interface TargetContract {
    data class State(
        val isLoading: Boolean = false,
        val goals: List<Goal> = emptyList()
    )

    sealed interface SideEffect {
        data object Back : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data object Refresh : Intent
    }
}
