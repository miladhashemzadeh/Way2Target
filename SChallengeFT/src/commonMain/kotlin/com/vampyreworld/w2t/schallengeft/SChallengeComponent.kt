package com.vampyreworld.w2t.schallengeft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Cost
import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.data.model.SolutionResult
import com.vampyreworld.w2t.domain.data.model.SolutionType
import com.vampyreworld.w2t.domain.usecase.AddChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface SChallengeComponent {
    val state: Value<SChallengeContract.State>
    val sideEffects: Flow<SChallengeContract.SideEffect>
    
    fun onIntent(intent: SChallengeContract.Intent)
}

class DefaultSChallengeComponent(
    componentContext: ComponentContext,
    private val addChallengeUseCase: AddChallengeUseCase,
    private val getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit
) : SChallengeComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        SChallengeContract.State(
            challenges = listOf(
                Challenges(1, null, "Resource Shortage", "Not enough memory for cache", Cost(0, 0, 0), 50, false, null, emptyList(), emptyList(), emptyList(), 0, null, null, emptyList())
            ),
            solutions = listOf(
                Solution(1, "Increase Cache Size", "Adjust configuration to 512MB", SolutionType.DIRECT_CONFRONTATION, Cost(0, 10, 0), 80, SolutionResult.IN_PROGRESS),
                Solution(2, "Implement LRU", "Add eviction policy to save memory", SolutionType.PLANNING, Cost(0, 40, 0), 90, SolutionResult.UNKNOWN)
            )
        )
    )
    override val state: Value<SChallengeContract.State> = _state

    private val _sideEffects = MutableSharedFlow<SChallengeContract.SideEffect>()
    override val sideEffects: Flow<SChallengeContract.SideEffect> = _sideEffects.asSharedFlow()

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
                // Handle add challenge
            }
            is SChallengeContract.Intent.OnStatusChange -> {
                // Handle status change
            }
            SChallengeContract.Intent.OnTakeAiHelp -> {}
            SChallengeContract.Intent.OnMakeDecision -> {}
            SChallengeContract.Intent.OnAddSolution -> {}
            is SChallengeContract.Intent.OnUpdateStabilityCondition -> {
                // Logic to update stability condition in the state
                _state.value = _state.value.copy(
                    selectedChallenge = _state.value.selectedChallenge?.let { challenge ->
                        challenge.copy(
                            stabilityConditions = challenge.stabilityConditions.map { 
                                if (it.id == intent.conditionId) it.copy(isMaintained = intent.isMaintained) else it
                            }
                        )
                    }
                )
            }
        }
    }
}
