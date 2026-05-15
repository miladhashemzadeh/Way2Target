package com.vampyreworld.w2t.targetft.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

class TargetStoreFactory(private val storeFactory: StoreFactory) {
    fun create(): TargetStore =
        object : TargetStore, Store<TargetStore.Intent, TargetStore.State, TargetStore.Label> by storeFactory.create(
            name = "TargetStore",
            initialState = TargetStore.State(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data object Loading : Msg
        data class Loaded(val targets: List<String>) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<TargetStore.Intent, Nothing, TargetStore.State, Msg, TargetStore.Label>() {
        override fun executeIntent(intent: TargetStore.Intent) {
            when (intent) {
                TargetStore.Intent.Refresh -> {
                    dispatch(Msg.Loading)
                    // Simulate loading
                    dispatch(Msg.Loaded(listOf("Target 1", "Target 2")))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<TargetStore.State, Msg> {
        override fun TargetStore.State.reduce(msg: Msg): TargetStore.State =
            when (msg) {
                Msg.Loading -> copy(isLoading = true)
                is Msg.Loaded -> copy(isLoading = false, targets = msg.targets)
            }
    }
}
