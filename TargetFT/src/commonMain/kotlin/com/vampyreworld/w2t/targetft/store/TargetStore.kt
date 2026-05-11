package com.vampyreworld.w2t.targetft.store

import com.arkivanov.mvikotlin.core.store.Store

interface TargetStore : Store<TargetStore.Intent, TargetStore.State, TargetStore.Label> {
    sealed interface Intent {
        data object Refresh : Intent
    }

    data class State(
        val isLoading: Boolean = false,
        val targets: List<String> = emptyList()
    )

    sealed interface Label {
        data class Error(val message: String) : Label
    }
}
