package com.vampyreworld.w2t.solutionft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.data.model.*
import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase
import com.vampyreworld.w2t.domain.usecase.GetSolutionsUseCase
import com.vampyreworld.w2t.domain.usecase.profile.GetUserProfileUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface SolutionComponent {
    val state: Value<SolutionContract.State>
    val sideEffects: Flow<SolutionContract.SideEffect>
    
    fun onIntent(intent: SolutionContract.Intent)
}

class DefaultSolutionComponent(
    componentContext: ComponentContext,
    private val addSolutionUseCase: AddSolutionUseCase,
    private val getSolutionsUseCase: GetSolutionsUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val goalId: Long?,
    private val challengeId: Long?,
    private val onBack: () -> Unit,
    private val onProfile: () -> Unit
) : SolutionComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(SolutionContract.State())
    override val state: Value<SolutionContract.State> = _state

    private val _sideEffects = MutableSharedFlow<SolutionContract.SideEffect>()
    override val sideEffects: Flow<SolutionContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        scope.launch {
            getUserProfileUseCase().collect { profile ->
                _state.value = _state.value.copy(
                    userName = profile.name,
                    avatarUrl = profile.avatarUrl
                )
            }
        }
    }

    private fun loadSolutions() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getSolutionsUseCase(goalId, challengeId).collect { solutions ->
                _state.value = _state.value.copy(solutions = solutions, isLoading = false)
            }
        }
    }

    override fun onIntent(intent: SolutionContract.Intent) {
        when (intent) {
            SolutionContract.Intent.OnBackClicked -> onBack()
            SolutionContract.Intent.OnProfileClicked -> onProfile()
            is SolutionContract.Intent.OnTitleChanged -> {
                _state.value = _state.value.copy(title = intent.title)
            }
            is SolutionContract.Intent.OnDescriptionChanged -> {
                _state.value = _state.value.copy(description = intent.description)
            }
            is SolutionContract.Intent.OnSolutionTypeChanged -> {
                _state.value = _state.value.copy(solutionType = intent.type)
            }
            SolutionContract.Intent.OnSaveClicked -> {
                saveSolution()
            }
            SolutionContract.Intent.OnGetAiInsightsClicked -> {
                // TODO: Implement AI insights
            }
        }
    }

    private fun saveSolution() {
        val currentState = _state.value
        if (currentState.title.isBlank()) return

        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val newSolution = Solution(
                    id = 0,
                    title = currentState.title,
                    desc = currentState.description,
                    solutionType = currentState.solutionType,
                    cost = Cost(energyCost = 10, timeCost = 10, moneyCost = 0),
                    aidStrength = 50,
                    result = SolutionResult.UNKNOWN
                )
                addSolutionUseCase(newSolution, challengeId)
                _sideEffects.emit(SolutionContract.SideEffect.ShowSuccess("Solution saved successfully!"))
                _state.value = _state.value.copy(
                    title = "",
                    description = "",
                    solutionType = SolutionType.PLANNING,
                    isLoading = false
                )
            } catch (e: Exception) {
                _sideEffects.emit(SolutionContract.SideEffect.ShowError(e.message ?: "Failed to save solution"))
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
