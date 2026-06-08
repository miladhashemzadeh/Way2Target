package com.vampyreworld.w2t.targetft.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.vampyreworld.w2t.sharedui.arch.asValue
import com.vampyreworld.w2t.targetft.presentation.intent.TargetMasterIntent
import com.vampyreworld.w2t.targetft.presentation.state.TargetMasterState
import com.vampyreworld.w2t.targetft.presentation.store.TargetMasterStore
import com.vampyreworld.w2t.targetft.presentation.store.TargetMasterStoreFactory
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultTargetMasterComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    getGoalsUseCase: GetGoalsUseCase,
    deleteGoalUseCase: DeleteGoalUseCase,
    private val onOutput: (TargetMasterComponent.Label) -> Unit
) : TargetMasterComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetMasterStoreFactory(storeFactory, getGoalsUseCase, deleteGoalUseCase).create()
    }

    override val state: Value<TargetMasterState> = store.asValue()

    override val labels: Flow<TargetMasterComponent.Label> = store.labels.map { label ->
        when (label) {
            is TargetMasterStore.Label.Error -> {
                // Handle error label if needed, or map it
                TargetMasterComponent.Label.Back // Placeholder
            }
        }
    }

    override fun onIntent(intent: TargetMasterIntent) {
        when (intent) {
            TargetMasterIntent.OnBackClick -> onOutput(TargetMasterComponent.Label.Back)
            TargetMasterIntent.OnAddGoalClick -> onOutput(TargetMasterComponent.Label.NavigateToDetail(null))
            is TargetMasterIntent.OnGoalClick -> onOutput(TargetMasterComponent.Label.NavigateToDetail(intent.goalId))
            TargetMasterIntent.Refresh -> store.accept(TargetMasterIntent.Refresh)
            is TargetMasterIntent.DeleteGoal -> store.accept(intent)
        }
    }
}
