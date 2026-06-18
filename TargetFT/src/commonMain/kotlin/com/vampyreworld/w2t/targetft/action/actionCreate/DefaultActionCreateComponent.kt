package com.vampyreworld.w2t.targetft.action.actionCreate

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
import com.vampyreworld.w2t.domain.data.model.Cost
import com.vampyreworld.w2t.sharedui.arch.asValue
import com.vampyreworld.w2t.targetft.store.TargetStore
import com.vampyreworld.w2t.targetft.store.TargetStoreFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class DefaultActionCreateComponent(
    componentContext: ComponentContext,
    private val parentId: Long?,
    storeFactory: StoreFactory,
    getGoalsUseCase: GetGoalsUseCase,
    saveGoalUseCase: SaveGoalUseCase,
    deleteGoalUseCase: DeleteGoalUseCase,
    getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit
) : ActionCreateContract.Component, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetStoreFactory(
            storeFactory,
            getGoalsUseCase,
            saveGoalUseCase,
            deleteGoalUseCase,
            getChallengesUseCase,
            goalId = null,
            initialTier = "ACTION",
            parentId = parentId,
            expectedTier = com.vampyreworld.w2t.domain.data.model.GoalTier.ACTION
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

    override val state: Value<ActionCreateContract.State> = store.asValue().map { mviState ->
        ActionCreateContract.State(
            isLoading = mviState.isLoading,
            parentId = mviState.parentId,
            error = mviState.error,
            title = "", // These should ideally be part of MVI state if we want to survive process death
            description = "",
            completionCriteria = "",
            energyCost = 50,
            timeCost = 50,
            moneyCost = 0,
            schedule = null
        )
    }

    override val sideEffects: Flow<ActionCreateContract.SideEffect> = store.labels.map { label ->
        when (label) {
            is TargetStore.Label.Error -> ActionCreateContract.SideEffect.ShowError(label.message)
            TargetStore.Label.Saved -> ActionCreateContract.SideEffect.Back
        }
    }

    override fun onIntent(intent: ActionCreateContract.Intent) {
        when (intent) {
            ActionCreateContract.Intent.OnBackClicked -> onBack()
            is ActionCreateContract.Intent.OnTitleChanged -> {}
            is ActionCreateContract.Intent.OnDescriptionChanged -> {}
            is ActionCreateContract.Intent.OnCriteriaChanged -> {}
            is ActionCreateContract.Intent.OnCostChanged -> {}
            is ActionCreateContract.Intent.OnScheduleChanged -> {}
            is ActionCreateContract.Intent.OnSaveGoal -> {
                store.accept(TargetStore.Intent.SaveGoal(
                    title = intent.title,
                    description = intent.description,
                    tier = "ACTION",
                    completionCriteria = intent.completionCriteria,
                    cost = Cost(
                        energyCost = intent.energyCost,
                        timeCost = intent.timeCost,
                        moneyCost = intent.moneyCost
                    ),
                    schedule = intent.schedule,
                    parentId = parentId
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
