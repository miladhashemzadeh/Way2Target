package com.vampyreworld.w2t.schallengeft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.usecase.AddChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengeByIdUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface SChallengeComponent {
    val state: Value<SChallengeContract.State>
    val sideEffects: Flow<SChallengeContract.SideEffect>
    
    fun onIntent(intent: SChallengeContract.Intent)
}

class DefaultSChallengeComponent(
    componentContext: ComponentContext,
    private val goalId: Long? = null,
    private val challengeId: Long? = null,
    private val addChallengeUseCase: AddChallengeUseCase,
    private val getChallengesUseCase: GetChallengesUseCase,
    private val getChallengeByIdUseCase: GetChallengeByIdUseCase,
    private val onBack: () -> Unit,
    private val navigateToAddSolution: (challengeId: Long) -> Unit = {}
) : SChallengeComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(SChallengeContract.State())
    override val state: Value<SChallengeContract.State> = _state

    private val _sideEffects = MutableSharedFlow<SChallengeContract.SideEffect>()
    override val sideEffects: Flow<SChallengeContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            if (challengeId != null) {
                getChallengeByIdUseCase(challengeId).collect { challenge ->
                    _state.value = _state.value.copy(selectedChallenge = challenge, isLoading = false)
                }
            } else if (goalId != null) {
                getChallengesUseCase(goalId).collect { challenges ->
                    _state.value = _state.value.copy(challenges = challenges, isLoading = false)
                }
            } else {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    override fun onIntent(intent: SChallengeContract.Intent) {
        when (intent) {
            SChallengeContract.Intent.OnBackClicked -> {
                if (_state.value.selectedChallenge != null) {
                    _state.value = _state.value.copy(selectedChallenge = null)
                } else {
                    onBack()
                }
            }
            is SChallengeContract.Intent.OnAddChallenge -> {
                addChallenge(intent.challenge)
            }
            is SChallengeContract.Intent.OnStatusChange -> {
                // Handle status change
            }
            SChallengeContract.Intent.OnTakeAiHelp -> {}
            SChallengeContract.Intent.OnMakeDecision -> {}
            SChallengeContract.Intent.OnAddSolution -> {
                _state.value.selectedChallenge?.id?.let { navigateToAddSolution(it) }
            }
            is SChallengeContract.Intent.OnUpdateStabilityCondition -> {
                updateStabilityCondition(intent)
            }
        }
    }

    private fun addChallenge(challenge: Challenges) {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                addChallengeUseCase(challenge)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun updateStabilityCondition(intent: SChallengeContract.Intent.OnUpdateStabilityCondition) {
        _state.value = _state.value.copy(
            selectedChallenge = _state.value.selectedChallenge?.let { challenge ->
                challenge.copy(
                    stabilityConditions = challenge.stabilityConditions.map { 
                        if (it.id == intent.conditionId) it.copy(isMaintained = intent.isMaintained) else it
                    }
                )
            }
        )
        // Here we should also save the updated challenge to the repository
        _state.value.selectedChallenge?.let { challenge ->
            scope.launch {
                addChallengeUseCase(challenge) // addChallengeUseCase also acts as update
            }
        }
    }
}
