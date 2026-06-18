package com.vampyreworld.w2t.profileft.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.vampyreworld.w2t.domain.data.model.UserProfile
import com.vampyreworld.w2t.domain.usecase.profile.GetUserProfileUseCase
import com.vampyreworld.w2t.domain.usecase.profile.SaveUserProfileUseCase
import com.vampyreworld.w2t.profileft.ProfileContract
import kotlinx.coroutines.launch

class ProfileStoreFactory(
    private val storeFactory: StoreFactory,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase
) {
    fun create(): ProfileStore =
        object : ProfileStore, Store<ProfileStore.Intent, ProfileContract.State, ProfileStore.Label> by storeFactory.create(
            name = "ProfileStore",
            initialState = ProfileContract.State(),
            bootstrapper = null,
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data class Loaded(val profile: UserProfile) : Msg
        data class Updating(val profile: UserProfile) : Msg
        data object Saving : Msg
        data object Saved : Msg
        data class SetEditMode(val isEdit: Boolean) : Msg
        data class Error(val message: String) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<ProfileStore.Intent, Nothing, ProfileContract.State, Msg, ProfileStore.Label>() {
        override fun executeIntent(intent: ProfileStore.Intent) {
            when (intent) {
                is ProfileStore.Intent.UpdateProfile -> dispatch(Msg.Updating(intent.profile))
                ProfileStore.Intent.SaveProfile -> saveProfile()
                is ProfileStore.Intent.SetEditMode -> dispatch(Msg.SetEditMode(intent.isEdit))
            }
        }

        init {
            scope.launch {
                getUserProfileUseCase().collect { profile ->
                    dispatch(Msg.Loaded(profile))
                }
            }
        }

        private fun saveProfile() {
            val currentProfile = state().profile
            scope.launch {
                dispatch(Msg.Saving)
                try {
                    saveUserProfileUseCase(currentProfile)
                    dispatch(Msg.Saved)
                    dispatch(Msg.SetEditMode(false))
                    publish(ProfileStore.Label.Saved)
                } catch (e: Exception) {
                    dispatch(Msg.Error(e.message ?: "Failed to save profile"))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<ProfileContract.State, Msg> {
        override fun ProfileContract.State.reduce(msg: Msg): ProfileContract.State =
            when (msg) {
                is Msg.Loaded -> copy(profile = msg.profile, isLoading = false)
                is Msg.Updating -> copy(profile = msg.profile)
                Msg.Saving -> copy(isSaving = true)
                Msg.Saved -> copy(isSaving = false)
                is Msg.SetEditMode -> copy(isEditMode = msg.isEdit)
                is Msg.Error -> copy(isSaving = false, error = msg.message)
            }
    }
}
