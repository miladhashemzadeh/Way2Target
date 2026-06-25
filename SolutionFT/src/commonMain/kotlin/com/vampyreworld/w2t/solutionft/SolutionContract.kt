package com.vampyreworld.w2t.solutionft

import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.data.model.SolutionType

interface SolutionContract {
    data class State(
        val isLoading: Boolean = false,
        val title: String = "",
        val description: String = "",
        val solutionType: SolutionType = SolutionType.PLANNING,
        val solutions: List<Solution> = emptyList(),
        val userName: String = "",
        val avatarUrl: String? = null
    )

    sealed interface SideEffect {
        data object Back : SideEffect
        data class ShowError(val message: String) : SideEffect
        data class ShowSuccess(val message: String) : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data object OnProfileClicked : Intent
        data class OnTitleChanged(val title: String) : Intent
        data class OnDescriptionChanged(val description: String) : Intent
        data class OnSolutionTypeChanged(val type: SolutionType) : Intent
        data object OnSaveClicked : Intent
        data object OnGetAiInsightsClicked : Intent
    }
}
