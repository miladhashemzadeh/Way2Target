package com.vampyreworld.w2t.schallengeft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Cost
import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.data.model.SolutionResult
import com.vampyreworld.w2t.domain.data.model.SolutionType
import com.vampyreworld.w2t.domain.data.model.StabilityCondition
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
    private val goalId: Long? = null,
    private val challengeId: Long? = null,
    private val addChallengeUseCase: AddChallengeUseCase,
    private val getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit,
    private val navigateToAddSolution: (challengeId: Long) -> Unit = {}
) : SChallengeComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        SChallengeContract.State(
            challenges = listOf(
                Challenges(
                    id = 1, 
                    solvingBeforeGoalId = null, 
                    title = "Resource Shortage", 
                    desc = "Not enough memory for cache", 
                    cost = Cost(0, 0, 0), 
                    priority = 50, 
                    isBarrier = false, 
                    parentGoalId = null, 
                    moodImpact = 0, 
                    prosAfterSolve = null, 
                    consAfterFailure = null,
                    stabilityConditions = emptyList()
                )
            ),
            selectedChallenge = if (challengeId != null) Challenges(
                id = challengeId,
                solvingBeforeGoalId = goalId,
                title = "Technical Barrier #$challengeId",
                desc = "Hard to implement X",
                cost = Cost(10, 50, 100),
                priority = 80,
                isBarrier = true,
                parentGoalId = goalId,
                moodImpact = 10,
                prosAfterSolve = null,
                consAfterFailure = null,
                stabilityConditions = listOf(
                    StabilityCondition(1, "Internet Access", "Stable connection required", true),
                    StabilityCondition(2, "Energy Level", "High focus needed", false)
                )
            ) else null,
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
            SChallengeContract.Intent.OnAddSolution -> {
                _state.value.selectedChallenge?.id?.let { navigateToAddSolution(it) }
            }
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
