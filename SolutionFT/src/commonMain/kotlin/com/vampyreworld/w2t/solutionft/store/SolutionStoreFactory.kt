package com.vampyreworld.w2t.solutionft.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.vampyreworld.w2t.domain.data.model.*
import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase
import com.vampyreworld.w2t.domain.usecase.GetSolutionsUseCase
import com.vampyreworld.w2t.solutionft.SolutionContract
import kotlinx.coroutines.launch

class SolutionStoreFactory(
    private val storeFactory: StoreFactory,
    private val addSolutionUseCase: AddSolutionUseCase,
    private val getSolutionsUseCase: GetSolutionsUseCase,
    private val goalId: Long? = null,
    private val challengeId: Long? = null
) {
    fun create(): SolutionStore =
        object : SolutionStore, Store<SolutionStore.Intent, SolutionContract.State, SolutionStore.Label> by storeFactory.create(
            name = "SolutionStore",
            initialState = SolutionContract.State(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data object Loading : Msg
        data class Loaded(val solutions: List<Solution>) : Msg
        data class TextChanged(val text: String) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<SolutionStore.Intent, Nothing, SolutionContract.State, Msg, SolutionStore.Label>() {
        override fun executeIntent(intent: SolutionStore.Intent) {
            when (intent) {
                SolutionStore.Intent.Refresh -> loadData()
                is SolutionStore.Intent.ChangeText -> dispatch(Msg.TextChanged(intent.text))
                SolutionStore.Intent.Save -> saveSolution()
            }
        }

        private fun loadData() {
            scope.launch {
                dispatch(Msg.Loading)
                getSolutionsUseCase(goalId, challengeId).collect { solutions ->
                    dispatch(Msg.Loaded(solutions))
                }
            }
        }

        private fun saveSolution() {
            val text = state().solutionText
            if (text.isBlank()) return

            scope.launch {
                dispatch(Msg.Loading)
                try {
                    val newSolution = Solution(
                        id = 0,
                        title = text,
                        desc = "",
                        solutionType = SolutionType.PLANNING,
                        cost = Cost(energyCost = 10, timeCost = 10, moneyCost = 0),
                        aidStrength = 50,
                        result = SolutionResult.UNKNOWN
                    )
                    addSolutionUseCase(newSolution)
                    dispatch(Msg.TextChanged(""))
                    loadData()
                } catch (e: Exception) {
                    publish(SolutionStore.Label.Error(e.message ?: "Failed to save solution"))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<SolutionContract.State, Msg> {
        override fun SolutionContract.State.reduce(msg: Msg): SolutionContract.State =
            when (msg) {
                Msg.Loading -> copy(isLoading = true)
                is Msg.Loaded -> copy(isLoading = false, solutions = msg.solutions)
                is Msg.TextChanged -> copy(solutionText = msg.text)
            }
    }
}
