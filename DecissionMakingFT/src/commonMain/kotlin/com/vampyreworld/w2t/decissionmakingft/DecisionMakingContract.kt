package com.vampyreworld.w2t.decissionmakingft

import com.vampyreworld.w2t.domain.data.model.Decision

interface DecisionMakingContract {
    data class State(
        val isLoading: Boolean = false,
        val decision: Decision? = null
    )

    sealed interface SideEffect {
        data object Back : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class OnMakeDecision(val decision: Decision) : Intent
    }
}
