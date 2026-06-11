package com.vampyreworld.w2t.targetft.master.masterCreate

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import com.vampyreworld.w2t.sharedui.arch.asValue
import com.vampyreworld.w2t.targetft.store.TargetStore
import com.vampyreworld.w2t.targetft.store.TargetStoreFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class DefaultMasterCreateComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    getGoalsUseCase: GetGoalsUseCase,
    saveGoalUseCase: SaveGoalUseCase,
    deleteGoalUseCase: DeleteGoalUseCase,
    getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit
) : MasterCreateContract.Component, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetStoreFactory(
            storeFactory,
            getGoalsUseCase,
            saveGoalUseCase,
            deleteGoalUseCase,
            getChallengesUseCase,
            goalId = null,
            initialTier = "MASTER",
            parentId = null
        ).create()
    }

    init {
        store.labels.onEach { label ->
            when (label) {
                TargetStore.Label.Saved -> onBack()
                is TargetStore.Label.Error -> {}
            }
        }.launchIn(componentScope())
    }

    override val state: Value<MasterCreateContract.State> = store.asValue().map { mviState ->
        MasterCreateContract.State(
            isLoading = mviState.isLoading,
            error = mviState.error
        )
    }

    override val sideEffects: Flow<MasterCreateContract.SideEffect> = store.labels.map { label ->
        when (label) {
            is TargetStore.Label.Error -> MasterCreateContract.SideEffect.ShowError(label.message)
            TargetStore.Label.Saved -> MasterCreateContract.SideEffect.Back
        }
    }

    override fun onIntent(intent: MasterCreateContract.Intent) {
        when (intent) {
            MasterCreateContract.Intent.OnBackClicked -> onBack()
            is MasterCreateContract.Intent.OnSaveGoal -> {
                store.accept(TargetStore.Intent.SaveGoal(intent.title, intent.description, "MASTER"))
            }
        }
    }
}

private fun <T : Any, R : Any> Value<T>.map(transform: (T) -> R): Value<R> {
    val mutableValue = com.arkivanov.decompose.value.MutableValue(transform(this.value))
    this.subscribe { mutableValue.value = transform(it) }
    return mutableValue
}
