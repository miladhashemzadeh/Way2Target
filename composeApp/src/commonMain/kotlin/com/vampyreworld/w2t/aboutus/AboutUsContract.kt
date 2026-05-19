package com.vampyreworld.w2t.aboutus

interface AboutUsContract {
    data class State(
        val version: String = "1.0.0"
    )

    sealed interface SideEffect {
        data object Back : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
    }
}
