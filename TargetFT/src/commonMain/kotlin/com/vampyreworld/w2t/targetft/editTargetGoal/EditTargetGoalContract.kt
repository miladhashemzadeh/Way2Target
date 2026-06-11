package com.vampyreworld.w2t.targetft.editTargetGoal

import com.vampyreworld.w2t.domain.data.model.Goal

interface EditTargetGoalContract {
    data class State(
        val masterGoal: Goal? = null,
        val milestones: List<Goal> = emptyList(),
        val actions: List<Goal> = emptyList(),
        val isLoading: Boolean = false,
        val newMilestoneTitle: String = "",
        val newActionTitle: String = "",
        val selectedMilestoneIdForAction: Long? = null
    )

    sealed interface SideEffect {
        data object Back : SideEffect
        data class ShowError(val message: String) : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class OnNewMilestoneTitleChanged(val title: String) : Intent
        data class OnNewActionTitleChanged(val title: String) : Intent
        data class OnMilestoneSelectedForAction(val milestoneId: Long?) : Intent
        data object OnAddMilestoneClicked : Intent
        data object OnAddActionClicked : Intent
        data class OnRemoveGoalClicked(val goalId: Long) : Intent
        data object OnFinishClicked : Intent
    }
}
