package com.vampyreworld.w2t.targetft.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.domain.data.model.GoalTier
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
    }

    private inner class ExecutorImpl : CoroutineExecutor<TargetStore.Intent, Nothing, TargetContract.State, Msg, TargetStore.Label>() {
        override fun executeIntent(intent: TargetStore.Intent) {
            when (intent) {
                TargetStore.Intent.Refresh -> {
                    loadGoals()
                }
                TargetStore.Intent.CancelGoal -> {
                    cancelGoal()
                }
                TargetStore.Intent.CreateChallenge -> {
                    // Handled by Component (Navigation)
                }
                TargetStore.Intent.CreateMilestone -> {
                    // Handled by Component (Navigation)
                }
                is TargetStore.Intent.DeleteSubGoal -> {
                    deleteGoal(intent.goalId)
                }
                is TargetStore.Intent.OnChallengeClick -> {
                    // Handled by Component (Navigation)
                }
                is TargetStore.Intent.ReplaceSubGoal -> {
                    // TODO: Handle ReplaceSubGoal
                }
                is TargetStore.Intent.SaveGoal -> {
                    saveGoal(intent)
                }
            }
        }

        private fun loadGoals() {
            scope.launch {
                dispatch(Msg.Loading)
                getGoalsUseCase().collect { goals ->
                    val selectedGoal = goals.find { it.id == goalId }
                    val relatedGoals = goals.filter { it.upperGoalId == goalId }
                    dispatch(Msg.Loaded(selectedGoal, relatedGoals))
                }
            }
        }

        private fun saveGoal(intent: TargetStore.Intent.SaveGoal) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    val newGoal = Goal(
                        id = 0, // Database will generate
                        title = intent.title,
                        description = intent.description,
                        tier = GoalTier.valueOf(intent.tier),
                        upperGoalId = state().parentId,
                        priority = 50 // Default
                    )
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
                    saveGoalUseCase(currentGoal.copy(status = GoalStatus.CANCELLED))
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
            }
    }
}
