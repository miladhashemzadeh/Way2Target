package com.vampyreworld.w2t.appraiseft

import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.Solution

interface AppraiseContract {
    data class State(
        val isLoading: Boolean = false,
        val goal: Goal? = null,
        val challenge: Challenges? = null,
        val solutions: List<Solution> = emptyList(),
        val selectedSolutionId: Long? = null,
        val challengeStatus: String = "Ongoing",
        val reflection: String = "",
        val targetId: Long? = null,
        val challengeId: Long? = null,
        val appraisalResult: String = ""
    )

    sealed interface SideEffect {
        data object Back : SideEffect
        data class ShowError(val message: String) : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data object OnAppraiseClicked : Intent
        data class OnChallengeStatusChanged(val status: String) : Intent
        data class OnSolutionSelected(val solutionId: Long) : Intent
        data class OnReflectionChanged(val reflection: String) : Intent
        data object OnUpdateChallengeClicked : Intent
        data object OnCompleteGoalClicked : Intent
        data object OnArchiveGoalClicked : Intent
    }
}
