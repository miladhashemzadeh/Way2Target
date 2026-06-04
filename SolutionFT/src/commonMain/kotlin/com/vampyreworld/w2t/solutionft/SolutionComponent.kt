package com.vampyreworld.w2t.solutionft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.data.model.*
import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase
import com.vampyreworld.w2t.domain.usecase.GetSolutionsUseCase
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
    private val goalId: Long?,
    private val challengeId: Long?,
    private val onBack: () -> Unit
) : SolutionComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(SolutionContract.State())
    override val state: Value<SolutionContract.State> = _state

    private val _sideEffects = MutableSharedFlow<SolutionContract.SideEffect>()
    override val sideEffects: Flow<SolutionContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        loadSolutions()
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
            is SolutionContract.Intent.OnSolutionTextChanged -> {
                _state.value = _state.value.copy(solutionText = intent.text)
            }
            SolutionContract.Intent.OnSaveClicked -> {
                saveSolution()
            }
        }
    }

    private fun saveSolution() {
        val text = _state.value.solutionText
        if (text.isBlank()) return

        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val newSolution = Solution(
                    id = 0,
                    title = text,
                    desc = "",
                    solutionType = SolutionType.PLANNING,
                    cost = Cost(energyCost = 10, timeCost = 10, moneyCost = 0),
                    aidStrength = 50,
                    result = SolutionResult.UNKNOWN
                )
                addSolutionUseCase(newSolution)
                _state.value = _state.value.copy(solutionText = "", isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
