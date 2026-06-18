package com.vampyreworld.w2t.targetft.store

import com.arkivanov.mvikotlin.core.store.Store
import com.vampyreworld.w2t.targetft.TargetContract

interface TargetStore : Store<TargetStore.Intent, TargetContract.State, TargetStore.Label> {
    sealed interface Intent {
        data object Refresh : Intent
        data object CreateChallenge : Intent
        data object NavigateToChallengeList : Intent
        data object NavigateToAppraise : Intent
        data object NavigateToDefineSteps : Intent
        data object CancelGoal : Intent
        data class SaveGoal(
            val title: String,
            val description: String,
            val tier: String,
            val isLifeGoal: Boolean = false,
            val isSkill: Boolean = false,
            val completionCriteria: String = "",
            val cost: com.vampyreworld.w2t.domain.data.model.Cost? = null
        ) : Intent
        data class SaveChallenge(val title: String, val description: String, val goalId: Long?, val impact: String) : Intent
        data class OnChallengeClick(val challengeId: Long) : Intent
        data class DeleteSubGoal(val goalId: Long) : Intent
        data class ReplaceSubGoal(val goalId: Long) : Intent
        data class UpdateGoal(val goal: com.vampyreworld.w2t.domain.data.model.Goal) : Intent
        data object Back : Intent
    }

    sealed interface Label {
        data object Saved : Label
        data class Error(val message: String) : Label
    }
}
