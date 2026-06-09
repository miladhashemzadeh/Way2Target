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
    }

    private inner class ExecutorImpl : CoroutineExecutor<AppraiseStore.Intent, Nothing, AppraiseContract.State, Msg, AppraiseStore.Label>() {
        override fun executeIntent(intent: AppraiseStore.Intent) {
            when (intent) {
                AppraiseStore.Intent.Appraise -> performAppraisal()
            }
        }

        private fun performAppraisal() {
            scope.launch {
                dispatch(Msg.Loading)
                delay(1000) // Simulate AI thinking
                dispatch(Msg.Result("AI Appraisal: This is a high-impact task with moderate risk."))
            }
        }
    }

    private object ReducerImpl : Reducer<AppraiseContract.State, Msg> {
        override fun AppraiseContract.State.reduce(msg: Msg): AppraiseContract.State =
            when (msg) {
                Msg.Loading -> copy(isLoading = true)
                is Msg.Result -> copy(isLoading = false, appraisalResult = msg.appraisal)
            }
    }
}
