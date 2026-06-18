package com.vampyreworld.w2t.targetft.milestone.milestoneDetail

import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.Challenges
import kotlinx.coroutines.flow.Flow

interface MilestoneDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val selectedGoal: Goal? = null,
        val actions: List<Goal> = emptyList(),
        val challenges: List<Challenges> = emptyList(),
        val parentId: Long? = null,
        val error: String? = null
    )

    sealed class SideEffect {
        object Back : SideEffect()
        data class ShowError(val message: String) : SideEffect()
    }

    sealed class Intent {
        object OnBackClicked : Intent()
        object Refresh : Intent()
        object CreateAction : Intent()
        object CancelGoal : Intent()
        object MakeDecision : Intent()
        object SetMood : Intent()
        object NavigateToChallengeList : Intent()
        object NavigateToAppraise : Intent()
        object NavigateToDefineSteps : Intent()
        data class OnGoalClick(val goalId: Long, val tier: String) : Intent()
        data class DeleteAction(val goalId: Long) : Intent()
        data class UpdateGoal(val goal: Goal) : Intent()
        object DeleteGoal : Intent()
    }

    interface Component {
        val state: Value<State>
        val sideEffects: Flow<SideEffect>
        fun onIntent(intent: Intent)
    }
}
