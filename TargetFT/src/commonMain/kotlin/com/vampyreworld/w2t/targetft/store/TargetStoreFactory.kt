package com.vampyreworld.w2t.targetft.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.targetft.TargetContract
import kotlinx.coroutines.launch

class TargetStoreFactory(
    private val storeFactory: StoreFactory,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase
) {
    fun create(): TargetStore =
        object : TargetStore, Store<TargetStore.Intent, TargetContract.State, TargetStore.Label> by storeFactory.create(
            name = "TargetStore",
            initialState = TargetContract.State(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data object Loading : Msg
        data class Loaded(val goals: List<Goal>) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<TargetStore.Intent, Nothing, TargetContract.State, Msg, TargetStore.Label>() {
        override fun executeIntent(intent: TargetStore.Intent) {
            when (intent) {
                TargetStore.Intent.Refresh -> {
                    loadGoals()
                }
                TargetStore.Intent.CancelGoal -> {
                    // TODO: Handle CancelGoal
                }
                TargetStore.Intent.CreateChallenge -> {
                    // TODO: Handle CreateChallenge
                }
                TargetStore.Intent.CreateMilestone -> {
                    // TODO: Handle CreateMilestone
                }
                is TargetStore.Intent.DeleteSubGoal -> {
                    // TODO: Handle DeleteSubGoal
                }
                is TargetStore.Intent.OnChallengeClick -> {
                    // TODO: Handle OnChallengeClick
                }
                is TargetStore.Intent.ReplaceSubGoal -> {
                    // TODO: Handle ReplaceSubGoal
                }
            }
        }

        private fun loadGoals() {
            scope.launch {
                dispatch(Msg.Loading)
                getGoalsUseCase().collect { goals ->
                    dispatch(Msg.Loaded(goals))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<TargetContract.State, Msg> {
        override fun TargetContract.State.reduce(msg: Msg): TargetContract.State =
            when (msg) {
                Msg.Loading -> copy(isLoading = true)
                is Msg.Loaded -> copy(isLoading = false, relatedGoals = msg.goals)
            }
    }
}
