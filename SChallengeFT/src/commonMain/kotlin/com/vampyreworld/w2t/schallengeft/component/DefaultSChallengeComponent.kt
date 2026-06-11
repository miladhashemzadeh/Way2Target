package com.vampyreworld.w2t.schallengeft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.schallengeft.SChallengeContract
import com.vampyreworld.w2t.schallengeft.store.SChallengeStore
import com.vampyreworld.w2t.schallengeft.store.SChallengeStoreFactory
import com.vampyreworld.w2t.sharedui.arch.asValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DefaultSChallengeComponent(
    componentContext: ComponentContext,
    storeFactory: SChallengeStoreFactory,
    private val onBack: () -> Unit,
    private val navigateToAddSolution: (challengeId: Long) -> Unit = {},
    private val navigateToDecision: (challengeId: Long) -> Unit = {}
) : SChallengeComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    private val scope = componentScope()

    override val state: Value<SChallengeContract.State> = store.asValue()

    private val _sideEffects = MutableSharedFlow<SChallengeContract.SideEffect>()
    override val sideEffects: Flow<SChallengeContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        scope.launch {
            store.labels.collect { label ->
                when (label) {
                    SChallengeStore.Label.Back -> onBack()
                    is SChallengeStore.Label.Error -> {
                        _sideEffects.emit(SChallengeContract.SideEffect.ShowError(label.message))
                    }
                }
            }
        }
        store.accept(SChallengeStore.Intent.Refresh)
    }

    override fun onIntent(intent: SChallengeContract.Intent) {
        when (intent) {
            SChallengeContract.Intent.OnBackClicked -> {
                if (state.value.selectedChallenge != null) {
                    store.accept(SChallengeStore.Intent.ClearSelectedChallenge)
                } else {
                    onBack()
                }
            }
            is SChallengeContract.Intent.OnChallengeClick -> {
                store.accept(SChallengeStore.Intent.OnChallengeClick(intent.challengeId))
            }
            is SChallengeContract.Intent.OnAddChallenge -> {
                store.accept(SChallengeStore.Intent.AddChallenge(intent.challenge))
            }
            is SChallengeContract.Intent.OnStatusChange -> {
                store.accept(SChallengeStore.Intent.OnStatusChange(intent.status))
            }
            SChallengeContract.Intent.OnTakeAiHelp -> {
                store.accept(SChallengeStore.Intent.OnTakeAiHelp)
            }
            SChallengeContract.Intent.OnMakeDecision -> {
                state.value.selectedChallenge?.id?.let { navigateToDecision(it) }
            }
            SChallengeContract.Intent.OnAddSolution -> {
                state.value.selectedChallenge?.id?.let { navigateToAddSolution(it) }
            }
            is SChallengeContract.Intent.OnUpdateStabilityCondition -> {
                store.accept(SChallengeStore.Intent.UpdateStabilityCondition(intent.conditionId, intent.isMaintained))
            }
        }
    }
}
