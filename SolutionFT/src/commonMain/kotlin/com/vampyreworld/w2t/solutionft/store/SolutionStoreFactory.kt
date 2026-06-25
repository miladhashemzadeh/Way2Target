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
        data class TitleChanged(val title: String) : Msg
        data class DescriptionChanged(val description: String) : Msg
        data class SolutionTypeChanged(val type: SolutionType) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<SolutionStore.Intent, Nothing, SolutionContract.State, Msg, SolutionStore.Label>() {
        override fun executeIntent(intent: SolutionStore.Intent) {
            when (intent) {
                SolutionStore.Intent.Refresh -> loadData()
                is SolutionStore.Intent.ChangeTitle -> dispatch(Msg.TitleChanged(intent.title))
                is SolutionStore.Intent.ChangeDescription -> dispatch(Msg.DescriptionChanged(intent.description))
                is SolutionStore.Intent.ChangeSolutionType -> dispatch(Msg.SolutionTypeChanged(intent.type))
                SolutionStore.Intent.Save -> saveSolution()
                SolutionStore.Intent.GetAiInsights -> getAiInsights()
            }
        }

        private fun getAiInsights() {
            scope.launch {
                dispatch(Msg.Loading)
                // Mock AI delay
                kotlinx.coroutines.delay(1500)
                // In a real app, this would call an AI service/use-case
                // For now, we just reload or show a message
                loadData()
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
            val title = state().title
            val description = state().description
            val type = state().solutionType
            if (title.isBlank()) return

            scope.launch {
                dispatch(Msg.Loading)
                try {
                    val newSolution = Solution(
                        id = 0,
                        title = title,
                        desc = description,
                        solutionType = type,
                        cost = Cost(energyCost = 10, timeCost = 10, moneyCost = 0),
                        aidStrength = 50,
                        result = SolutionResult.UNKNOWN
                    )
                    addSolutionUseCase(newSolution)
                    dispatch(Msg.TitleChanged(""))
                    dispatch(Msg.DescriptionChanged(""))
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
                is Msg.TitleChanged -> copy(title = msg.title)
                is Msg.DescriptionChanged -> copy(description = msg.description)
                is Msg.SolutionTypeChanged -> copy(solutionType = msg.type)
            }
    }
}
