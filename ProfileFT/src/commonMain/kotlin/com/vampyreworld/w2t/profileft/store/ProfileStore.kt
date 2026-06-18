package com.vampyreworld.w2t.profileft.store

import com.arkivanov.mvikotlin.core.store.Store
import com.vampyreworld.w2t.domain.data.model.UserProfile
import com.vampyreworld.w2t.profileft.ProfileContract

interface ProfileStore : Store<ProfileStore.Intent, ProfileContract.State, ProfileStore.Label> {
    sealed interface Intent {
        data class UpdateProfile(val profile: UserProfile) : Intent
        data object SaveProfile : Intent
        data class SetEditMode(val isEdit: Boolean) : Intent
    }

    sealed interface Label {
        data object Saved : Label
        data class Error(val message: String) : Label
    }
}
