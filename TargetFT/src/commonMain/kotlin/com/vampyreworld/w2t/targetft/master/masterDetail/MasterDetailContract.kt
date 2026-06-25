package com.vampyreworld.w2t.targetft.master.masterDetail

import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.Challenges
import kotlinx.coroutines.flow.Flow

interface MasterDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val selectedGoal: Goal? = null,
        val milestones: List<Goal> = emptyList(),
        val challenges: List<Challenges> = emptyList(),
        val error: String? = null
    )

    sealed class SideEffect {
        object Back : SideEffect()
        data class ShowError(val message: String) : SideEffect()
    }

    sealed class Intent {
        object OnBackClicked : Intent()
        object Refresh : Intent()
        object CreateMilestone : Intent()
        object CancelGoal : Intent()
        object MakeDecision : Intent()
        object SetMood : Intent()
        object NavigateToChallengeList : Intent()
        object NavigateToAppraise : Intent()
        object NavigateToDefineSteps : Intent()
        data class OnGoalClick(val goalId: Long, val tier: String) : Intent()
        data class OnChallengeClick(val challengeId: Long) : Intent()
        data class DeleteMilestone(val goalId: Long) : Intent()
        data class UpdateGoal(val goal: Goal) : Intent()
        data class OnSaveChallenge(val title: String, val description: String, val goalId: Long?, val impact: String) : Intent()
        object DeleteGoal : Intent()
    }

    interface Component {
        val state: Value<State>
        val sideEffects: Flow<SideEffect>
        fun onIntent(intent: Intent)
    }
}
