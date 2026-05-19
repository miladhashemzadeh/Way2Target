package com.vampyreworld.w2t.targetft.store

import com.arkivanov.mvikotlin.core.store.Store
import com.vampyreworld.w2t.targetft.TargetContract

interface TargetStore : Store<TargetStore.Intent, TargetContract.State, TargetStore.Label> {
    sealed interface Intent {
        data object Refresh : Intent
    }

    // State is now TargetContract.State

    sealed interface Label {
        data class Error(val message: String) : Label
    }
}
