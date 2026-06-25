package com.vampyreworld.w2t.profileft

import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileContract {
    data class State(
        val isLoading: Boolean = true,
        val profile: UserProfile = UserProfile(),
        val isSaving: Boolean = false,
        val isEditMode: Boolean = false,
        val error: String? = null
    )

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class UpdateProfile(val profile: UserProfile) : Intent
        data object SaveProfile : Intent
        data class SetEditMode(val isEdit: Boolean) : Intent
    }

    sealed interface SideEffect {
        data object Back : SideEffect
        data class ShowError(val message: String) : SideEffect
    }

    interface Component {
        val state: Value<State>
        val sideEffects: Flow<SideEffect>
        fun onIntent(intent: Intent)
        fun onNavigateToHome() {}
        fun onNavigateToProfile() {}
        fun onNavigateToSChallenge() {}
        fun onNavigateToPreferences() {}
    }
}
