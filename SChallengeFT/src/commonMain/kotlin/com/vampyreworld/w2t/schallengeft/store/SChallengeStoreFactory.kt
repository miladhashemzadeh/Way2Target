package com.vampyreworld.w2t.schallengeft.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.usecase.AddChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.DeleteChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengeByIdUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import com.vampyreworld.w2t.schallengeft.SChallengeContract
import kotlinx.coroutines.launch

class SChallengeStoreFactory(
    private val storeFactory: StoreFactory,
    private val addChallengeUseCase: AddChallengeUseCase,
    private val deleteChallengeUseCase: DeleteChallengeUseCase,
    private val getChallengesUseCase: GetChallengesUseCase,
    private val getChallengeByIdUseCase: GetChallengeByIdUseCase,
    private val goalId: Long? = null,
    private val challengeId: Long? = null
) {
    fun create(): SChallengeStore =
        object : SChallengeStore, Store<SChallengeStore.Intent, SChallengeContract.State, SChallengeStore.Label> by storeFactory.create(
            name = "SChallengeStore",
            initialState = SChallengeContract.State(goalId = goalId),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data object Loading : Msg
        data class Loaded(val challenges: List<Challenges>, val selectedChallenge: Challenges?) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<SChallengeStore.Intent, Nothing, SChallengeContract.State, Msg, SChallengeStore.Label>() {
        override fun executeIntent(intent: SChallengeStore.Intent) {
            when (intent) {
                SChallengeStore.Intent.Refresh -> loadData()
                is SChallengeStore.Intent.OnChallengeClick -> loadChallenge(intent.challengeId)
                is SChallengeStore.Intent.AddChallenge -> addChallenge(intent.challenge)
                is SChallengeStore.Intent.UpdateStabilityCondition -> updateStabilityCondition(intent)
                is SChallengeStore.Intent.DeleteChallenge -> deleteChallenge(intent.id)
                SChallengeStore.Intent.OnTakeAiHelp -> takeAiHelp()
                SChallengeStore.Intent.ClearSelectedChallenge -> dispatch(Msg.Loaded(state().challenges, null))
                else -> {}
            }
        }

        private fun deleteChallenge(id: Long) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    deleteChallengeUseCase(id)
                    loadData()
                } catch (e: Exception) {
                    publish(SChallengeStore.Label.Error(e.message ?: "Failed to delete challenge"))
                }
            }
        }

        private fun takeAiHelp() {
            // Implementation for AI help would go here
            // For now, maybe just a side effect or log
            publish(SChallengeStore.Label.Error("AI Help is not implemented yet"))
        }

        private fun loadChallenge(id: Long) {
            scope.launch {
                dispatch(Msg.Loading)
                getChallengeByIdUseCase(id).collect { challenge ->
                    dispatch(Msg.Loaded(state().challenges, challenge))
                }
            }
        }

        private fun loadData() {
            scope.launch {
                dispatch(Msg.Loading)
                val currentGoalId = goalId ?: state().goalId
                if (challengeId != null) {
                    getChallengeByIdUseCase(challengeId).collect { challenge ->
                        dispatch(Msg.Loaded(emptyList(), challenge))
                    }
                } else if (currentGoalId != null) {
                    getChallengesUseCase(currentGoalId).collect { challenges ->
                        dispatch(Msg.Loaded(challenges, null))
                    }
                } else {
                    dispatch(Msg.Loaded(emptyList(), null))
                }
            }
        }

        private fun addChallenge(challenge: Challenges) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    addChallengeUseCase(challenge)
                    loadData()
                } catch (e: Exception) {
                    publish(SChallengeStore.Label.Error(e.message ?: "Failed to add challenge"))
                }
            }
        }

        private fun updateStabilityCondition(intent: SChallengeStore.Intent.UpdateStabilityCondition) {
            val challenge = state().selectedChallenge ?: return
            val updatedChallenge = challenge.copy(
                stabilityConditions = challenge.stabilityConditions.map {
                    if (it.id == intent.conditionId) it.copy(isMaintained = intent.isMaintained) else it
                }
            )
            scope.launch {
                try {
                    addChallengeUseCase(updatedChallenge)
                } catch (e: Exception) {
                    publish(SChallengeStore.Label.Error(e.message ?: "Failed to update condition"))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<SChallengeContract.State, Msg> {
        override fun SChallengeContract.State.reduce(msg: Msg): SChallengeContract.State =
            when (msg) {
                Msg.Loading -> copy(isLoading = true)
                is Msg.Loaded -> copy(
                    isLoading = false,
                    challenges = msg.challenges,
                    selectedChallenge = msg.selectedChallenge
                )
            }
    }
}
