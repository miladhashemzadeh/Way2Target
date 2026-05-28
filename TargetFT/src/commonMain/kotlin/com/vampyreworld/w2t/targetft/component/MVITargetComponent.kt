package com.vampyreworld.w2t.targetft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.vampyreworld.w2t.sharedui.arch.asValue
import com.vampyreworld.w2t.targetft.TargetContract
import com.vampyreworld.w2t.targetft.store.TargetStore
import com.vampyreworld.w2t.targetft.store.TargetStoreFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MVITargetComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val onBack: () -> Unit,
    private val navigateToDecision: (Long) -> Unit = {},
    private val navigateToMood: () -> Unit = {}
) : TargetComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetStoreFactory(storeFactory).create()
    }

    override val state: Value<TargetContract.State> = store.asValue()

    override val sideEffects: Flow<TargetContract.SideEffect> = store.labels.map { label ->
        when (label) {
            is TargetStore.Label.Error -> {
                // For now, map all labels to Back as a placeholder, 
                // or you might want to add more SideEffects to TargetContract
                TargetContract.SideEffect.Back
            }
        }
    }

    override fun onIntent(intent: TargetContract.Intent) {
        when (intent) {
            TargetContract.Intent.OnBackClicked -> onBack()
            TargetContract.Intent.Refresh -> store.accept(TargetStore.Intent.Refresh)
            TargetContract.Intent.CancelGoal -> store.accept(TargetStore.Intent.CancelGoal)
            TargetContract.Intent.CreateChallenge -> store.accept(TargetStore.Intent.CreateChallenge)
            TargetContract.Intent.CreateMilestone -> store.accept(TargetStore.Intent.CreateMilestone)
            TargetContract.Intent.MakeDecision -> {
                state.value.selectedGoal?.id?.let(navigateToDecision)
            }
            TargetContract.Intent.SetMood -> navigateToMood()
            is TargetContract.Intent.OnChallengeClick -> store.accept(TargetStore.Intent.OnChallengeClick(intent.challengeId))
            is TargetContract.Intent.DeleteSubGoal -> store.accept(TargetStore.Intent.DeleteSubGoal(intent.goalId))
            is TargetContract.Intent.ReplaceSubGoal -> store.accept(TargetStore.Intent.ReplaceSubGoal(intent.goalId))
        }
    }
}
