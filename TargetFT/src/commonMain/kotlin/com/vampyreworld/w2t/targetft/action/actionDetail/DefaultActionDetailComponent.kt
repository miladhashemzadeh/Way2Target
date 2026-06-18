package com.vampyreworld.w2t.targetft.action.actionDetail

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

class DefaultActionDetailComponent(
    componentContext: ComponentContext,
    private val goalId: Long,
    private val parentId: Long?,
    storeFactory: StoreFactory,
    getGoalsUseCase: GetGoalsUseCase,
    saveGoalUseCase: SaveGoalUseCase,
    deleteGoalUseCase: DeleteGoalUseCase,
    getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit,
    private val navigateToDecision: (Long) -> Unit,
    private val navigateToMood: () -> Unit,
    private val navigateToGoal: (Long, String) -> Unit,
    private val navigateToChallenge: (goalId: Long) -> Unit,
    private val navigateToAppraise: (goalId: Long) -> Unit
) : ActionDetailContract.Component, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetStoreFactory(
            storeFactory,
            getGoalsUseCase,
            saveGoalUseCase,
            deleteGoalUseCase,
            getChallengesUseCase,
            goalId = goalId,
            initialTier = "ACTION",
            parentId = parentId,
            expectedTier = com.vampyreworld.w2t.domain.data.model.GoalTier.ACTION
        ).create()
    }

    init {
        store.accept(TargetStore.Intent.Refresh)
    }

    override val state: Value<ActionDetailContract.State> = store.asValue().map { mviState ->
        ActionDetailContract.State(
            isLoading = mviState.isLoading,
            selectedGoal = mviState.selectedGoal,
            challenges = mviState.challenges,
            parentId = mviState.parentId,
            error = mviState.error
        )
    }

    override val sideEffects: Flow<ActionDetailContract.SideEffect> = store.labels.map { label ->
        when (label) {
            is TargetStore.Label.Error -> ActionDetailContract.SideEffect.ShowError(label.message)
            TargetStore.Label.Saved -> ActionDetailContract.SideEffect.Back
        }
    }

    override fun onIntent(intent: ActionDetailContract.Intent) {
        when (intent) {
            ActionDetailContract.Intent.OnBackClicked -> onBack()
            ActionDetailContract.Intent.Refresh -> store.accept(TargetStore.Intent.Refresh)
            ActionDetailContract.Intent.CancelGoal -> store.accept(TargetStore.Intent.CancelGoal)
            ActionDetailContract.Intent.MakeDecision -> navigateToDecision(goalId)
            ActionDetailContract.Intent.SetMood -> navigateToMood()
            ActionDetailContract.Intent.NavigateToChallengeList -> navigateToChallenge(goalId)
            ActionDetailContract.Intent.NavigateToAppraise -> navigateToAppraise(goalId)
            ActionDetailContract.Intent.NavigateToDefineSteps -> store.accept(TargetStore.Intent.NavigateToDefineSteps)
            is ActionDetailContract.Intent.OnGoalClick -> navigateToGoal(intent.goalId, intent.tier)
            is ActionDetailContract.Intent.UpdateGoal -> store.accept(TargetStore.Intent.UpdateGoal(intent.goal))
        }
    }
}

private fun <T : Any, R : Any> Value<T>.map(transform: (T) -> R): Value<R> {
    val mutableValue = com.arkivanov.decompose.value.MutableValue(transform(this.value))
    this.subscribe { mutableValue.value = transform(it) }
    return mutableValue
}
