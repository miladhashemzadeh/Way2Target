package com.vampyreworld.w2t.targetft

import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Goal

interface TargetContract {
    data class State(
        val isLoading: Boolean = false,
        val selectedGoal: Goal? = null,
        val relatedGoals: List<Goal> = emptyList(),
        val challenges: List<Challenges> = emptyList(),
        val initialTier: String? = null,
        val parentId: Long? = null
    )

    sealed interface SideEffect {
        data object Back : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data object Refresh : Intent
        data object CreateChallenge : Intent
        data object CreateChildGoal : Intent
        data object CancelGoal : Intent
        data object MakeDecision : Intent
        data object SetMood : Intent
        data class OnSaveGoal(val title: String, val description: String, val tier: String) : Intent
        data class OnChallengeClick(val challengeId: Long) : Intent
        data class DeleteSubGoal(val goalId: Long) : Intent
        data class ReplaceSubGoal(val goalId: Long) : Intent
        data class OnGoalClick(val goalId: Long) : Intent
        data class UpdateGoal(val goal: Goal) : Intent
    }
}
