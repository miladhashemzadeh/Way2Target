package com.vampyreworld.w2t.schallengeft.ui.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Cost
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.domain.usecase.AddChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface ChallengeCreateComponent {
    val state: Value<ChallengeCreateContract.State>
    val sideEffects: Flow<ChallengeCreateContract.SideEffect>
    
    fun onIntent(intent: ChallengeCreateContract.Intent)
}

class DefaultChallengeCreateComponent(
    componentContext: ComponentContext,
    private val goalId: Long?,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val addChallengeUseCase: AddChallengeUseCase,
    private val onBack: () -> Unit
) : ChallengeCreateComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(ChallengeCreateContract.State(selectedGoalId = goalId))
    override val state: Value<ChallengeCreateContract.State> = _state

    private val _sideEffects = MutableSharedFlow<ChallengeCreateContract.SideEffect>()
    override val sideEffects: Flow<ChallengeCreateContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        loadGoals()
    }

    private fun loadGoals() {
        scope.launch {
            getGoalsUseCase().collect { goals ->
                _state.value = _state.value.copy(availableGoals = goals)
            }
        }
    }

    override fun onIntent(intent: ChallengeCreateContract.Intent) {
        when (intent) {
            ChallengeCreateContract.Intent.OnBackClicked -> onBack()
            is ChallengeCreateContract.Intent.OnTitleChanged -> {
                _state.value = _state.value.copy(title = intent.title)
            }
            is ChallengeCreateContract.Intent.OnDescriptionChanged -> {
                _state.value = _state.value.copy(description = intent.description)
            }
            is ChallengeCreateContract.Intent.OnGoalSelected -> {
                _state.value = _state.value.copy(selectedGoalId = intent.goalId)
            }
            is ChallengeCreateContract.Intent.OnImpactLevelSelected -> {
                _state.value = _state.value.copy(impactLevel = intent.impactLevel)
            }
            ChallengeCreateContract.Intent.OnCreateClicked -> createChallenge()
        }
    }

    private fun createChallenge() {
        val currentState = _state.value
        println("DefaultChallengeCreateComponent: createChallenge title=${currentState.title} goalId=${currentState.selectedGoalId}")
        if (currentState.title.isBlank()) {
            println("DefaultChallengeCreateComponent: title is blank, aborting")
            return
        }

        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val challenge = Challenges(
                    id = 0,
                    title = currentState.title,
                    desc = currentState.description,
                    parentGoalId = currentState.selectedGoalId,
                    cost = Cost(energyCost = 50, timeCost = 50, moneyCost = 0), // Default medium effort
                    priority = when (currentState.impactLevel) {
                        ChallengeCreateContract.ImpactLevel.LOW -> 30
                        ChallengeCreateContract.ImpactLevel.MEDIUM -> 60
                        ChallengeCreateContract.ImpactLevel.HIGH -> 90
                    },
                    status = GoalStatus.ACTIVE,
                    isBarrier = currentState.impactLevel == ChallengeCreateContract.ImpactLevel.HIGH
                )
                println("DefaultChallengeCreateComponent: saving challenge $challenge")
                addChallengeUseCase(challenge)
                println("DefaultChallengeCreateComponent: save success, navigating back")
                onBack()
            } catch (e: Exception) {
                println("DefaultChallengeCreateComponent: save error ${e.message}")
                _sideEffects.emit(ChallengeCreateContract.SideEffect.ShowError(e.message ?: "Unknown error"))
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
