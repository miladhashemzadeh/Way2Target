package com.vampyreworld.w2t.targetft.master.masterDetail

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
import com.vampyreworld.w2t.domain.data.model.MilestoneGoal
import com.vampyreworld.w2t.sharedui.arch.asValue
import com.vampyreworld.w2t.targetft.store.TargetStore
import com.vampyreworld.w2t.targetft.store.TargetStoreFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class DefaultMasterDetailComponent(
    componentContext: ComponentContext,
    private val goalId: Long,
    storeFactory: StoreFactory,
    getGoalsUseCase: GetGoalsUseCase,
    saveGoalUseCase: SaveGoalUseCase,
    deleteGoalUseCase: DeleteGoalUseCase,
    getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit,
    private val navigateToDecision: (Long) -> Unit,
    private val navigateToMood: () -> Unit,
    private val navigateToGoal: (Long, String) -> Unit,
    private val navigateToCreateMilestone: (parentId: Long) -> Unit,
    private val navigateToChallenge: (goalId: Long) -> Unit,
    private val navigateToAppraise: (goalId: Long) -> Unit,
    private val navigateToSolution: (goalId: Long, challengeId: Long) -> Unit
) : MasterDetailContract.Component, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetStoreFactory(
            storeFactory,
            getGoalsUseCase,
            saveGoalUseCase,
            deleteGoalUseCase,
            getChallengesUseCase,
            goalId = goalId,
            initialTier = "MASTER",
            parentId = null,
            expectedTier = com.vampyreworld.w2t.domain.data.model.GoalTier.MASTER
        ).create()
    }

    init {
        store.accept(TargetStore.Intent.Refresh)
    }

    override val state: Value<MasterDetailContract.State> = store.asValue().map { mviState ->
        MasterDetailContract.State(
            isLoading = mviState.isLoading,
            selectedGoal = mviState.selectedGoal,
            milestones = mviState.relatedGoals.filterIsInstance<MilestoneGoal>().filter { 
                it.masterGoalId == goalId
            },
            challenges = mviState.challenges,
            error = mviState.error
        )
    }

    override val sideEffects: Flow<MasterDetailContract.SideEffect> = store.labels.map { label ->
        when (label) {
            is TargetStore.Label.Error -> MasterDetailContract.SideEffect.ShowError(label.message)
            TargetStore.Label.Saved -> MasterDetailContract.SideEffect.Back
        }
    }

    override fun onIntent(intent: MasterDetailContract.Intent) {
        when (intent) {
            MasterDetailContract.Intent.OnBackClicked -> onBack()
            MasterDetailContract.Intent.Refresh -> store.accept(TargetStore.Intent.Refresh)
            MasterDetailContract.Intent.CreateMilestone -> navigateToCreateMilestone(goalId)
            MasterDetailContract.Intent.CancelGoal -> store.accept(TargetStore.Intent.CancelGoal)
            MasterDetailContract.Intent.MakeDecision -> navigateToDecision(goalId)
            MasterDetailContract.Intent.SetMood -> navigateToMood()
            MasterDetailContract.Intent.NavigateToChallengeList -> navigateToChallenge(goalId)
            MasterDetailContract.Intent.NavigateToAppraise -> navigateToAppraise(goalId)
            MasterDetailContract.Intent.NavigateToDefineSteps -> store.accept(TargetStore.Intent.NavigateToDefineSteps)
            is MasterDetailContract.Intent.OnGoalClick -> navigateToGoal(intent.goalId, intent.tier)
            is MasterDetailContract.Intent.OnChallengeClick -> navigateToSolution(goalId, intent.challengeId)
            is MasterDetailContract.Intent.DeleteMilestone -> store.accept(TargetStore.Intent.DeleteSubGoal(intent.goalId))
            is MasterDetailContract.Intent.UpdateGoal -> store.accept(TargetStore.Intent.UpdateGoal(intent.goal))
            is MasterDetailContract.Intent.OnSaveChallenge -> store.accept(TargetStore.Intent.SaveChallenge(intent.title, intent.description, intent.goalId, intent.impact))
            MasterDetailContract.Intent.DeleteGoal -> store.accept(TargetStore.Intent.DeleteSubGoal(goalId))
        }
    }
}

private fun <T : Any, R : Any> Value<T>.map(transform: (T) -> R): Value<R> {
    val mutableValue = com.arkivanov.decompose.value.MutableValue(transform(this.value))
    this.subscribe { mutableValue.value = transform(it) }
    return mutableValue
}
