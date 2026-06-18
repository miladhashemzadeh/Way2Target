package com.vampyreworld.w2t.profileft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.vampyreworld.w2t.domain.usecase.profile.GetUserProfileUseCase
import com.vampyreworld.w2t.domain.usecase.profile.SaveUserProfileUseCase
import com.vampyreworld.w2t.profileft.ProfileContract
import com.vampyreworld.w2t.profileft.store.ProfileStore
import com.vampyreworld.w2t.profileft.store.ProfileStoreFactory
import com.vampyreworld.w2t.sharedui.arch.asValue
import com.vampyreworld.w2t.sharedui.arch.componentScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DefaultProfileComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    getUserProfileUseCase: GetUserProfileUseCase,
    saveUserProfileUseCase: SaveUserProfileUseCase,
    private val onBack: () -> Unit
) : ProfileContract.Component, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        ProfileStoreFactory(
            storeFactory = storeFactory,
            getUserProfileUseCase = getUserProfileUseCase,
            saveUserProfileUseCase = saveUserProfileUseCase
        ).create()
    }

    override val state: Value<ProfileContract.State> = store.asValue()

    private val scope = componentScope()

    private val _sideEffects = MutableSharedFlow<ProfileContract.SideEffect>()
    override val sideEffects: Flow<ProfileContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        scope.launch {
            store.labels.collect { label ->
                when (label) {
                    ProfileStore.Label.Saved -> {} // Handled by toggling state
                    is ProfileStore.Label.Error -> _sideEffects.emit(ProfileContract.SideEffect.ShowError(label.message))
                }
            }
        }
    }

    override fun onIntent(intent: ProfileContract.Intent) {
        when (intent) {
            ProfileContract.Intent.OnBackClicked -> onBack()
            is ProfileContract.Intent.UpdateProfile -> store.accept(ProfileStore.Intent.UpdateProfile(intent.profile))
            ProfileContract.Intent.SaveProfile -> store.accept(ProfileStore.Intent.SaveProfile)
            is ProfileContract.Intent.SetEditMode -> store.accept(ProfileStore.Intent.SetEditMode(intent.isEdit))
        }
    }
}
