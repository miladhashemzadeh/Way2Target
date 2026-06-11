package com.vampyreworld.w2t.targetft.detailActionGoal

import com.vampyreworld.w2t.domain.data.model.ActionGoal
import com.vampyreworld.w2t.domain.data.model.Goal

interface ActionGoalDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val actionGoal: ActionGoal? = null,
        val masterGoal: Goal? = null,
        val milestone: Goal? = null
    )

    sealed interface SideEffect {
        data object Back : SideEffect
        data class ShowError(val message: String) : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data object OnEditClicked : Intent
        data object OnMarkCompleteClicked : Intent
        data class OnGoalLinkClick(val goalId: Long) : Intent
    }
}
