package com.vampyreworld.w2t.shomeft

import com.vampyreworld.w2t.domain.data.model.Goal

interface HomeContract {
    data class State(
        val userName: String = "User",
        val isLoading: Boolean = false,
        val masterGoals: List<Goal> = emptyList()
    )

    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
    }

    sealed interface Intent {
        data object OnProfileClick : Intent
        data object CreateMasterGoal : Intent
        data class OnMasterGoalClick(val goalId: Long) : Intent
        data class DeleteMasterGoal(val goalId: Long) : Intent
        data class CreateChallengeForMasterGoal(val goalId: Long) : Intent
    }
}
