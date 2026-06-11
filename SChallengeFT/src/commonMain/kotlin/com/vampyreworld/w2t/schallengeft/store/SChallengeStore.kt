package com.vampyreworld.w2t.schallengeft.store

import com.arkivanov.mvikotlin.core.store.Store
import com.vampyreworld.w2t.schallengeft.SChallengeContract

interface SChallengeStore : Store<SChallengeStore.Intent, SChallengeContract.State, SChallengeStore.Label> {
    sealed interface Intent {
        data object Refresh : Intent
        data class OnChallengeClick(val challengeId: Long) : Intent
        data class AddChallenge(val challenge: com.vampyreworld.w2t.domain.data.model.Challenges) : Intent
        data class UpdateStabilityCondition(val conditionId: Long, val isMaintained: Boolean) : Intent
        data class OnStatusChange(val status: String) : Intent
        data object OnTakeAiHelp : Intent
        data object ClearSelectedChallenge : Intent
    }

    sealed interface Label {
        data object Back : Label
        data class Error(val message: String) : Label
    }
}
