package com.vampyreworld.w2t.targetft.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.vampyreworld.w2t.targetft.presentation.intent.TargetMasterIntent
import com.vampyreworld.w2t.targetft.presentation.state.TargetMasterState

interface TargetMasterStore : Store<TargetMasterIntent, TargetMasterState, TargetMasterStore.Label> {
    sealed interface Label {
        data class Error(val message: String) : Label
    }
}
