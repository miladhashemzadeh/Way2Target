package com.vampyreworld.w2t.schallengeft

import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Solution

interface SChallengeContract {
    data class State(
        val goalId: Long? = null,
        val isLoading: Boolean = false,
        val challenges: List<Challenges> = emptyList(),
        val selectedChallenge: Challenges? = null,
        val solutions: List<Solution> = emptyList()
    )

    sealed interface SideEffect {
        data object Back : SideEffect
        data class ShowError(val message: String) : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class OnChallengeClick(val challengeId: Long) : Intent
        data class OnAddChallenge(val challenge: Challenges) : Intent
        data object OnTakeAiHelp : Intent
        data object OnMakeDecision : Intent
        data object OnAddSolution : Intent
        data class OnUpdateStabilityCondition(val conditionId: Long, val isMaintained: Boolean) : Intent
    }
}
