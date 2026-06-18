package com.vampyreworld.w2t.targetft.master.masterCreate

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow

interface MasterCreateContract {
    data class State(
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed class SideEffect {
        object Back : SideEffect()
        data class ShowError(val message: String) : SideEffect()
    }

    sealed class Intent {
        object OnBackClicked : Intent()
        data class OnSaveGoal(val title: String, val description: String, val isLifeGoal: Boolean) : Intent()
    }

    interface Component {
        val state: Value<State>
        val sideEffects: Flow<SideEffect>
        fun onIntent(intent: Intent)
    }
}
