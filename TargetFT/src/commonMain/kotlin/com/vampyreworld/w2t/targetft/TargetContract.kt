package com.vampyreworld.w2t.targetft

import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.Challenges

interface TargetContract {
    data class State(
        val isLoading: Boolean = false,
        val selectedGoal: Goal? = null,
        val relatedGoals: List<Goal> = emptyList(),
        val challenges: List<Challenges> = emptyList(),
        val initialTier: String? = null,
        val parentId: Long? = null,
        val currentScreen: Screen = Screen.DETAIL,
        val error: String? = null
    )

    sealed class Screen {
        object DETAIL : Screen()
        object CREATE_GOAL : Screen()
        object DEFINE_STEPS : Screen()
    }

    sealed class SideEffect {
        object Back : SideEffect()
        data class ShowError(val message: String) : SideEffect()
    }

    sealed class Intent {
        object OnBackClicked : Intent()
        object Refresh : Intent()
        object CreateChallenge : Intent()
        object CreateChildGoal : Intent()
        object CancelGoal : Intent()
        object MakeDecision : Intent()
        object SetMood : Intent()
        object NavigateToChallengeList : Intent()
        object NavigateToAppraise : Intent()
        object NavigateToDefineSteps : Intent()
        data class OnSaveGoal(val title: String, val description: String, val tier: String) : Intent()
        data class OnSaveChallenge(val title: String, val description: String, val goalId: Long?, val impact: String) : Intent()
        data class OnChallengeClick(val challengeId: Long) : Intent()
        data class DeleteSubGoal(val goalId: Long) : Intent()
        data class ReplaceSubGoal(val goalId: Long) : Intent()
        data class OnGoalClick(val goalId: Long) : Intent()
        data class UpdateGoal(val goal: Goal) : Intent()
    }
}
