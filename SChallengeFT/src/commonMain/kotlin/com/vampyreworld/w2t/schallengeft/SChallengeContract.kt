package com.vampyreworld.w2t.schallengeft

import com.vampyreworld.w2t.domain.data.model.Challenges

interface SChallengeContract {
    data class State(
        val isLoading: Boolean = false,
        val challenges: List<Challenges> = emptyList()
    )

    sealed interface SideEffect {
        data object Back : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class OnAddChallenge(val challenge: Challenges) : Intent
    }
}
