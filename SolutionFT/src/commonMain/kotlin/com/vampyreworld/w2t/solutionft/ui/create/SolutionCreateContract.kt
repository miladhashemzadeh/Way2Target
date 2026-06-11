package com.vampyreworld.w2t.solutionft.ui.create

import com.vampyreworld.w2t.domain.data.model.SolutionType

interface SolutionCreateContract {
    data class State(
        val isLoading: Boolean = false,
        val description: String = "",
        val source: SolutionSource = SolutionSource.MY_IDEA,
        val solutionType: SolutionType = SolutionType.DIRECT_CONFRONTATION,
        val challengeId: Long? = null
    )

    enum class SolutionSource {
        MY_IDEA, AI_ASSISTANT, EXTERNAL_RESOURCE
    }

    sealed interface SideEffect {
        data object Back : SideEffect
        data class ShowError(val message: String) : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class OnDescriptionChanged(val description: String) : Intent
        data class OnSourceChanged(val source: SolutionSource) : Intent
        data class OnSolutionTypeChanged(val type: SolutionType) : Intent
        data object OnAddSolutionClicked : Intent
    }
}
