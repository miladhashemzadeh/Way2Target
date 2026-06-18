package com.vampyreworld.w2t.targetft.milestone.milestoneDetail

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

class DefaultMilestoneDetailComponent(
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
    private val navigateToCreateAction: (parentId: Long) -> Unit,
    private val navigateToChallenge: (goalId: Long) -> Unit,
    private val navigateToAppraise: (goalId: Long) -> Unit
) : MilestoneDetailContract.Component, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetStoreFactory(
            storeFactory,
            getGoalsUseCase,
            saveGoalUseCase,
            deleteGoalUseCase,
            getChallengesUseCase,
            goalId = goalId,
            initialTier = null,
            parentId = parentId
        ).create()
    }

    init {
        store.accept(TargetStore.Intent.Refresh)
    }

    override val state: Value<MilestoneDetailContract.State> = store.asValue().map { mviState ->
        MilestoneDetailContract.State(
            isLoading = mviState.isLoading,
            selectedGoal = mviState.selectedGoal,
            actions = mviState.relatedGoals,
            challenges = mviState.challenges,
            parentId = mviState.parentId,
            error = mviState.error
        )
    }

    override val sideEffects: Flow<MilestoneDetailContract.SideEffect> = store.labels.map { label ->
        when (label) {
            is TargetStore.Label.Error -> MilestoneDetailContract.SideEffect.ShowError(label.message)
            TargetStore.Label.Saved -> MilestoneDetailContract.SideEffect.Back
        }
    }

    override fun onIntent(intent: MilestoneDetailContract.Intent) {
        when (intent) {
            MilestoneDetailContract.Intent.OnBackClicked -> onBack()
            MilestoneDetailContract.Intent.Refresh -> store.accept(TargetStore.Intent.Refresh)
            MilestoneDetailContract.Intent.CreateAction -> navigateToCreateAction(goalId)
            MilestoneDetailContract.Intent.CancelGoal -> store.accept(TargetStore.Intent.CancelGoal)
            MilestoneDetailContract.Intent.MakeDecision -> navigateToDecision(goalId)
            MilestoneDetailContract.Intent.SetMood -> navigateToMood()
            MilestoneDetailContract.Intent.NavigateToChallengeList -> navigateToChallenge(goalId)
            MilestoneDetailContract.Intent.NavigateToAppraise -> navigateToAppraise(goalId)
            MilestoneDetailContract.Intent.NavigateToDefineSteps -> store.accept(TargetStore.Intent.NavigateToDefineSteps)
            is MilestoneDetailContract.Intent.OnGoalClick -> navigateToGoal(intent.goalId, intent.tier)
            is MilestoneDetailContract.Intent.DeleteAction -> store.accept(TargetStore.Intent.DeleteSubGoal(intent.goalId))
            is MilestoneDetailContract.Intent.UpdateGoal -> store.accept(TargetStore.Intent.UpdateGoal(intent.goal))
        }
    }
}

private fun <T : Any, R : Any> Value<T>.map(transform: (T) -> R): Value<R> {
    val mutableValue = com.arkivanov.decompose.value.MutableValue(transform(this.value))
    this.subscribe { mutableValue.value = transform(it) }
    return mutableValue
}
