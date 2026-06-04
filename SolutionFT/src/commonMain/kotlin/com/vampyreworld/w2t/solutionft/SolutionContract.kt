package com.vampyreworld.w2t.solutionft

import com.vampyreworld.w2t.domain.data.model.Solution

interface SolutionContract {
    data class State(
        val isLoading: Boolean = false,
        val solutionText: String = "",
        val solutions: List<Solution> = emptyList()
    )

    sealed interface SideEffect {
        data object Back : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class OnSolutionTextChanged(val text: String) : Intent
        data object OnSaveClicked : Intent
    }
}
