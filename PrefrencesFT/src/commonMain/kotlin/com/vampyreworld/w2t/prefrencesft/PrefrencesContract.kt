package com.vampyreworld.w2t.prefrencesft

interface PrefrencesContract {
    data class State(
        val isLoading: Boolean = false,
        val isDarkMode: Boolean = false,
        val language: String = "en"
    )

    sealed interface SideEffect {
        data object Back : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class OnThemeChanged(val isDarkMode: Boolean) : Intent
        data class OnLanguageChanged(val language: String) : Intent
    }
}
