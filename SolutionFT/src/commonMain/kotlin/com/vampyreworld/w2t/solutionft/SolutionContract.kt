package com.vampyreworld.w2t.solutionft

import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.data.model.SolutionType

interface SolutionContract {
    data class State(
        val isLoading: Boolean = false,
        val title: String = "",
        val description: String = "",
        val solutionType: SolutionType = SolutionType.PLANNING,
        val energyCost: Int = 10,
        val timeCost: Int = 10,
        val moneyCost: Int = 0,
        val aidStrength: Int = 50,
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
        data class OnEnergyCostChanged(val cost: Int) : Intent
        data class OnTimeCostChanged(val cost: Int) : Intent
        data class OnMoneyCostChanged(val cost: Int) : Intent
        data class OnAidStrengthChanged(val strength: Int) : Intent
        data object OnSaveClicked : Intent
        data object OnGetAiInsightsClicked : Intent
    }
}
