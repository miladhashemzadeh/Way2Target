package com.vampyreworld.w2t.targetft.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.vampyreworld.w2t.domain.data.model.*
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.targetft.TargetContract
import kotlinx.coroutines.launch

class TargetStoreFactory(
    private val storeFactory: StoreFactory,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val goalId: Long? = null,
    private val initialTier: String? = null,
    private val parentId: Long? = null
) {
    fun create(): TargetStore =
        object : TargetStore, Store<TargetStore.Intent, TargetContract.State, TargetStore.Label> by storeFactory.create(
            name = "TargetStore",
            initialState = TargetContract.State(
                initialTier = initialTier,
                parentId = parentId
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data object Loading : Msg
        data class Loaded(val selectedGoal: Goal?, val relatedGoals: List<Goal>) : Msg
        data class SetScreen(val screen: TargetContract.Screen) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<TargetStore.Intent, Nothing, TargetContract.State, Msg, TargetStore.Label>() {
        override fun executeIntent(intent: TargetStore.Intent) {
            when (intent) {
                TargetStore.Intent.Refresh -> {
                    loadData()
                }
                TargetStore.Intent.CancelGoal -> {
                    cancelGoal()
                }
                TargetStore.Intent.CreateChallenge -> {
                    // Handled by component navigation
                }
                TargetStore.Intent.NavigateToChallengeList -> {
                    // Handled by component navigation
                }
                TargetStore.Intent.NavigateToAppraise -> {
                    // Handled by component navigation
                }
                TargetStore.Intent.NavigateToDefineSteps -> {
                    dispatch(Msg.SetScreen(TargetContract.Screen.DEFINE_STEPS))
                }
                TargetStore.Intent.Back -> {
                    if (state().currentScreen != TargetContract.Screen.DETAIL) {
                        dispatch(Msg.SetScreen(TargetContract.Screen.DETAIL))
                    }
                }
                is TargetStore.Intent.DeleteSubGoal -> {
                    deleteGoal(intent.goalId)
                }
                is TargetStore.Intent.OnChallengeClick -> {
                    // Handled by component navigation
                }
                is TargetStore.Intent.ReplaceSubGoal -> {
                }
                is TargetStore.Intent.SaveGoal -> {
                    saveGoal(intent)
                }
                is TargetStore.Intent.SaveChallenge -> {
                    // Handled by component navigation
                }
                is TargetStore.Intent.UpdateGoal -> {
                    updateGoal(intent.goal)
                }
            }
        }

        private fun updateGoal(goal: Goal) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    saveGoalUseCase(goal)
                } catch (e: Exception) {
                    publish(TargetStore.Label.Error(e.message ?: "Failed to update goal"))
                }
            }
        }

        private fun loadData() {
            scope.launch {
                dispatch(Msg.Loading)
                getGoalsUseCase().collect { goals ->
                    val selectedGoal = goals.find { it.id == goalId }
                    // Load all goals if we are in detail view, so we can show the tree
                    val relatedGoals = if (goalId != null) goals else emptyList()
                    dispatch(Msg.Loaded(selectedGoal, relatedGoals))
                }
            }
        }

        private fun saveGoal(intent: TargetStore.Intent.SaveGoal) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    val tier = GoalTier.valueOf(intent.tier)
                    val newGoal = when (tier) {
                        GoalTier.MASTER -> MasterGoal(
                            id = 0,
                            title = intent.title,
                            description = intent.description,
                            priority = 50,
                            status = GoalStatus.ACTIVE
                        )
                        GoalTier.MILESTONE -> MilestoneGoal(
                            id = 0,
                            title = intent.title,
                            description = intent.description,
                            priority = 50,
                            status = GoalStatus.ACTIVE,
                            masterGoalId = state().parentId ?: 0L
                        )
                        GoalTier.ACTION -> ActionGoal(
                            id = 0,
                            title = intent.title,
                            description = intent.description,
                            priority = 50,
                            status = GoalStatus.ACTIVE,
                            milestoneGoalId = state().parentId ?: 0L
                        )
                    }
                    saveGoalUseCase(newGoal)
                    publish(TargetStore.Label.Saved)
                } catch (e: Exception) {
                    publish(TargetStore.Label.Error(e.message ?: "Unknown error"))
                }
            }
        }

        private fun cancelGoal() {
            val currentGoal = state().selectedGoal ?: return
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    saveGoalUseCase(currentGoal.withStatus(GoalStatus.CANCELLED))
                    publish(TargetStore.Label.Saved) // Or a specific label for cancellation
                } catch (e: Exception) {
                    publish(TargetStore.Label.Error(e.message ?: "Failed to cancel goal"))
                }
            }
        }

        private fun deleteGoal(id: Long) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    deleteGoalUseCase(id)
                    // Refresh is automatic since getGoalsUseCase is a Flow
                } catch (e: Exception) {
                    publish(TargetStore.Label.Error(e.message ?: "Failed to delete goal"))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<TargetContract.State, Msg> {
        override fun TargetContract.State.reduce(msg: Msg): TargetContract.State =
            when (msg) {
                Msg.Loading -> copy(isLoading = true)
                is Msg.Loaded -> copy(
                    isLoading = false,
                    selectedGoal = msg.selectedGoal,
                    relatedGoals = msg.relatedGoals
                )
                is Msg.SetScreen -> copy(currentScreen = msg.screen)
            }
    }
}
