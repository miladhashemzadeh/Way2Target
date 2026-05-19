package com.vampyreworld.w2t.shomeft

interface HomeContract {
    data class State(
        val userName: String = "",
        val isLoading: Boolean = false
    )

    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
    }

    sealed interface Intent {
        data object OnProfileClick : Intent
    }
}
