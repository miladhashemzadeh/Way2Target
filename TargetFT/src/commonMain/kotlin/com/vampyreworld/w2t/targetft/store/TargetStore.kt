package com.vampyreworld.w2t.targetft.store

import com.arkivanov.mvikotlin.core.store.Store
import com.vampyreworld.w2t.targetft.TargetContract

interface TargetStore : Store<TargetStore.Intent, TargetContract.State, TargetStore.Label> {
    sealed interface Intent {
        data object Refresh : Intent
        data object CreateChallenge : Intent
        data object CreateMilestone : Intent
        data object CancelGoal : Intent
        data class SaveGoal(val title: String, val description: String, val tier: String) : Intent
        data class OnChallengeClick(val challengeId: Long) : Intent
        data class DeleteSubGoal(val goalId: Long) : Intent
        data class ReplaceSubGoal(val goalId: Long) : Intent
        data class UpdateGoal(val goal: com.vampyreworld.w2t.domain.data.model.Goal) : Intent
    }

    // State is now TargetContract.State

    sealed interface Label {
        data object Saved : Label
        data class Error(val message: String) : Label
    }
}
