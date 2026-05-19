package com.vampyreworld.w2t.moodaddft

import com.vampyreworld.w2t.domain.data.model.UserMood

interface MoodAddContract {
    data class State(
        val isLoading: Boolean = false,
        val moodHistory: List<UserMood> = emptyList()
    )

    sealed interface SideEffect {
        data object Back : SideEffect
    }

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class OnAddMood(val mood: UserMood) : Intent
    }
}
