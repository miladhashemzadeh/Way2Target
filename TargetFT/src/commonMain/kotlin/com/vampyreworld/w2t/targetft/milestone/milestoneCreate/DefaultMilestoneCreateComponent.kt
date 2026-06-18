package com.vampyreworld.w2t.targetft.milestone.milestoneCreate

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

class DefaultMilestoneCreateComponent(
    componentContext: ComponentContext,
    private val parentId: Long?,
    storeFactory: StoreFactory,
    getGoalsUseCase: GetGoalsUseCase,
    saveGoalUseCase: SaveGoalUseCase,
    deleteGoalUseCase: DeleteGoalUseCase,
    getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit
) : MilestoneCreateContract.Component, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetStoreFactory(
            storeFactory,
            getGoalsUseCase,
            saveGoalUseCase,
            deleteGoalUseCase,
            getChallengesUseCase,
            goalId = null,
            initialTier = "MILESTONE",
            parentId = parentId
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

    override val state: Value<MilestoneCreateContract.State> = store.asValue().map { mviState ->
        MilestoneCreateContract.State(
            isLoading = mviState.isLoading,
            parentId = mviState.parentId,
            error = mviState.error
        )
    }

    override val sideEffects: Flow<MilestoneCreateContract.SideEffect> = store.labels.map { label ->
        when (label) {
            is TargetStore.Label.Error -> MilestoneCreateContract.SideEffect.ShowError(label.message)
            TargetStore.Label.Saved -> MilestoneCreateContract.SideEffect.Back
        }
    }

    override fun onIntent(intent: MilestoneCreateContract.Intent) {
        when (intent) {
            MilestoneCreateContract.Intent.OnBackClicked -> onBack()
            is MilestoneCreateContract.Intent.OnSaveGoal -> {
                store.accept(TargetStore.Intent.SaveGoal(
                    title = intent.title,
                    description = intent.description,
                    tier = "MILESTONE",
                    isSkill = intent.isSkill
                ))
            }
        }
    }
}

private fun <T : Any, R : Any> Value<T>.map(transform: (T) -> R): Value<R> {
    val mutableValue = com.arkivanov.decompose.value.MutableValue(transform(this.value))
    this.subscribe { mutableValue.value = transform(it) }
    return mutableValue
}
