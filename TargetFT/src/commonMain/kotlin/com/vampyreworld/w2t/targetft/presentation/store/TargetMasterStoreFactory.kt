package com.vampyreworld.w2t.targetft.presentation.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.targetft.presentation.intent.TargetMasterIntent
import com.vampyreworld.w2t.targetft.presentation.state.TargetMasterState
import kotlinx.coroutines.launch

class TargetMasterStoreFactory(
    private val storeFactory: StoreFactory,
    private val getGoalsUseCase: GetGoalsUseCase
) {
    fun create(): TargetMasterStore =
        object : TargetMasterStore, Store<TargetMasterIntent, TargetMasterState, TargetMasterStore.Label> by storeFactory.create(
            name = "TargetMasterStore",
            initialState = TargetMasterState(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data object Loading : Msg
        data class Loaded(val goals: com.vampyreworld.w2t.domain.data.model.Goal) : Msg // Wait, should be list
        data class Error(val message: String) : Msg
        data class GoalsLoaded(val goals: List<com.vampyreworld.w2t.domain.data.model.Goal>) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<TargetMasterIntent, Unit, TargetMasterState, Msg, TargetMasterStore.Label>() {
        override fun executeAction(action: Unit) {
            loadGoals()
        }

        override fun executeIntent(intent: TargetMasterIntent) {
            when (intent) {
                TargetMasterIntent.Refresh -> loadGoals()
                else -> {} // Handle other intents or labels
            }
        }

        private fun loadGoals() {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    // Assuming GetGoalsUseCase returns a Flow or a List
                    // Existing GetGoalsUseCase returned a List.
                    val goals = getGoalsUseCase() 
                    dispatch(Msg.GoalsLoaded(goals))
                } catch (e: Exception) {
                    dispatch(Msg.Error(e.message ?: "Unknown error"))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<TargetMasterState, Msg> {
        override fun TargetMasterState.reduce(msg: Msg): TargetMasterState =
            when (msg) {
                is Msg.Loading -> copy(isLoading = true, error = null)
                is Msg.GoalsLoaded -> copy(isLoading = false, goals = msg.goals)
                is Msg.Error -> copy(isLoading = false, error = msg.message)
                else -> this
            }
    }
}
