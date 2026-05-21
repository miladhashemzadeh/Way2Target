package com.vampyreworld.w2t.schallengeft

import com.vampyreworld.w2t.domain.data.model.Challenges

interface SChallengeContract {
    data class State(
        val isLoading: Boolean = false,
        val challenges: List<Challenges> = emptyList(),
        val selectedChallenge: Challenges? = null
    )

    sealed interface SideEffect {
        data object Back : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class OnAddChallenge(val challenge: Challenges) : Intent
        data class OnStatusChange(val status: String) : Intent
        data object OnTakeAiHelp : Intent
        data object OnMakeDecision : Intent
        data object OnAddSolution : Intent
    }
}
