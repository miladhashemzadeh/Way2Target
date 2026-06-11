package com.vampyreworld.w2t.appraiseft.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.vampyreworld.w2t.appraiseft.AppraiseContract
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppraiseStoreFactory(
    private val storeFactory: StoreFactory,
    private val targetId: Long? = null,
    private val challengeId: Long? = null
) {
    fun create(): AppraiseStore =
        object : AppraiseStore, Store<AppraiseStore.Intent, AppraiseContract.State, AppraiseStore.Label> by storeFactory.create(
            name = "AppraiseStore",
            initialState = AppraiseContract.State(targetId = targetId, challengeId = challengeId),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data object Loading : Msg
        data class Result(val appraisal: String) : Msg
        data class UpdateChallengeStatus(val status: String) : Msg
        data class UpdateSelectedSolution(val solutionId: Long) : Msg
        data class UpdateReflection(val reflection: String) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<AppraiseStore.Intent, Nothing, AppraiseContract.State, Msg, AppraiseStore.Label>() {
        override fun executeIntent(intent: AppraiseStore.Intent) {
            when (intent) {
                AppraiseStore.Intent.Back -> publish(AppraiseStore.Label.Back)
                AppraiseStore.Intent.Appraise -> performUpdate()
                is AppraiseStore.Intent.ChangeChallengeStatus -> dispatch(Msg.UpdateChallengeStatus(intent.status))
                is AppraiseStore.Intent.SelectSolution -> dispatch(Msg.UpdateSelectedSolution(intent.solutionId))
                is AppraiseStore.Intent.ChangeReflection -> dispatch(Msg.UpdateReflection(intent.reflection))
                AppraiseStore.Intent.UpdateChallenge -> performUpdate()
                AppraiseStore.Intent.CompleteGoal -> performCompleteGoal()
                AppraiseStore.Intent.ArchiveGoal -> performArchiveGoal()
            }
        }

        private fun performUpdate() {
            scope.launch {
                dispatch(Msg.Loading)
                delay(1000)
                dispatch(Msg.Result("Updated successfully"))
            }
        }

        private fun performCompleteGoal() {
            // Implementation for completing goal
        }

        private fun performArchiveGoal() {
            // Implementation for archiving goal
        }
    }

    private object ReducerImpl : Reducer<AppraiseContract.State, Msg> {
        override fun AppraiseContract.State.reduce(msg: Msg): AppraiseContract.State =
            when (msg) {
                Msg.Loading -> copy(isLoading = true)
                is Msg.Result -> copy(isLoading = false, appraisalResult = msg.appraisal)
                is Msg.UpdateChallengeStatus -> copy(challengeStatus = msg.status)
                is Msg.UpdateSelectedSolution -> copy(selectedSolutionId = msg.solutionId)
                is Msg.UpdateReflection -> copy(reflection = msg.reflection)
            }
    }
}
