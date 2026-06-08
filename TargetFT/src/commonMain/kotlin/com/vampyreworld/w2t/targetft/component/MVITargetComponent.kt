package com.vampyreworld.w2t.targetft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.vampyreworld.w2t.domain.data.model.GoalTier
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.sharedui.arch.asValue
import com.vampyreworld.w2t.targetft.TargetContract
import com.vampyreworld.w2t.targetft.store.TargetStore
import com.vampyreworld.w2t.targetft.store.TargetStore.Intent.*
import com.vampyreworld.w2t.targetft.store.TargetStoreFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MVITargetComponent(
    componentContext: ComponentContext,
    private val goalId: Long?,
    private val initialTier: String? = null,
    private val parentId: Long? = null,
    storeFactory: StoreFactory,
    getGoalsUseCase: GetGoalsUseCase,
    saveGoalUseCase: SaveGoalUseCase,
    deleteGoalUseCase: DeleteGoalUseCase,
    private val onBack: () -> Unit,
    private val navigateToDecision: (Long) -> Unit = {},
    private val navigateToMood: () -> Unit = {},
    private val navigateToChildTarget: (parentId: Long, tier: String) -> Unit = { _, _ -> },
    private val navigateToChallenge: (goalId: Long) -> Unit = {},
    private val navigateToChallengeDetail: (goalId: Long, challengeId: Long) -> Unit = { _, _ -> }
) : TargetComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetStoreFactory(
            storeFactory,
            getGoalsUseCase,
            saveGoalUseCase,
            deleteGoalUseCase,
            goalId = goalId,
            initialTier = initialTier,
            parentId = parentId
        ).create()
    }

    init {
        store.accept(TargetStore.Intent.Refresh)
    }

    override val state: Value<TargetContract.State> = store.asValue()

    override val sideEffects: Flow<TargetContract.SideEffect> = store.labels.map { label ->
        when (label) {
            is TargetStore.Label.Error -> {
                // TODO: Show error message
                TargetContract.SideEffect.Back
            }
            TargetStore.Label.Saved -> {
                TargetContract.SideEffect.Back
            }
        }
    }

    override fun onIntent(intent: TargetContract.Intent) {
        when (intent) {
            TargetContract.Intent.OnBackClicked -> onBack()
            TargetContract.Intent.Refresh -> store.accept(TargetStore.Intent.Refresh)
            TargetContract.Intent.CancelGoal -> store.accept(TargetStore.Intent.CancelGoal)
            TargetContract.Intent.CreateChallenge -> {
                state.value.selectedGoal?.id?.let(navigateToChallenge)
            }
            TargetContract.Intent.CreateChildGoal -> {
                state.value.selectedGoal?.let { currentGoal ->
                    val childTier = when (currentGoal.tier) {
                        GoalTier.MASTER -> "MILESTONE"
                        GoalTier.MILESTONE -> "ACTION"
                        GoalTier.ACTION -> "ACTION"
                    }
                    navigateToChildTarget(currentGoal.id, childTier)
                }
            }
            TargetContract.Intent.MakeDecision -> {
                state.value.selectedGoal?.id?.let(navigateToDecision)
            }
            TargetContract.Intent.SetMood -> navigateToMood()
            is TargetContract.Intent.OnChallengeClick -> {
                state.value.selectedGoal?.id?.let { goalId ->
                    navigateToChallengeDetail(goalId, intent.challengeId)
                }
            }
            is TargetContract.Intent.DeleteSubGoal -> store.accept(DeleteSubGoal(intent.goalId))
            is TargetContract.Intent.ReplaceSubGoal -> store.accept(ReplaceSubGoal(intent.goalId))
            is TargetContract.Intent.OnSaveGoal -> store.accept(SaveGoal(intent.title, intent.description, intent.tier))
        }
    }
}
