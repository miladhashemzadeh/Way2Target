package com.vampyreworld.w2t.solutionft.store

import com.arkivanov.mvikotlin.core.store.Store
import com.vampyreworld.w2t.domain.data.model.SolutionType
import com.vampyreworld.w2t.solutionft.SolutionContract

interface SolutionStore : Store<SolutionStore.Intent, SolutionContract.State, SolutionStore.Label> {
    sealed interface Intent {
        data object Refresh : Intent
        data class ChangeTitle(val title: String) : Intent
        data class ChangeDescription(val description: String) : Intent
        data class ChangeSolutionType(val type: SolutionType) : Intent
        data object Save : Intent
        data object GetAiInsights : Intent
    }

    sealed interface Label {
        data object Back : Label
        data class Error(val message: String) : Label
    }
}
