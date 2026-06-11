package com.vampyreworld.w2t.schallengeft.ui.create

import com.vampyreworld.w2t.domain.data.model.Goal

interface ChallengeCreateContract {
    data class State(
        val isLoading: Boolean = false,
        val title: String = "",
        val description: String = "",
        val selectedGoalId: Long? = null,
        val impactLevel: ImpactLevel = ImpactLevel.MEDIUM,
        val availableGoals: List<Goal> = emptyList()
    )

    enum class ImpactLevel {
        LOW, MEDIUM, HIGH
    }

    sealed interface SideEffect {
        data object Back : SideEffect
        data class ShowError(val message: String) : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class OnTitleChanged(val title: String) : Intent
        data class OnDescriptionChanged(val description: String) : Intent
        data class OnGoalSelected(val goalId: Long?) : Intent
        data class OnImpactLevelSelected(val impactLevel: ImpactLevel) : Intent
        data object OnCreateClicked : Intent
    }
}
