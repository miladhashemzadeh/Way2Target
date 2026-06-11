package com.vampyreworld.w2t.solutionft.ui.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.data.model.Cost
import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.data.model.SolutionResult
import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface SolutionCreateComponent {
    val state: Value<SolutionCreateContract.State>
    val sideEffects: Flow<SolutionCreateContract.SideEffect>
    
    fun onIntent(intent: SolutionCreateContract.Intent)
}

class DefaultSolutionCreateComponent(
    componentContext: ComponentContext,
    private val challengeId: Long?,
    private val addSolutionUseCase: AddSolutionUseCase,
    private val onBack: () -> Unit
) : SolutionCreateComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(SolutionCreateContract.State(challengeId = challengeId))
    override val state: Value<SolutionCreateContract.State> = _state

    private val _sideEffects = MutableSharedFlow<SolutionCreateContract.SideEffect>()
    override val sideEffects: Flow<SolutionCreateContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: SolutionCreateContract.Intent) {
        when (intent) {
            SolutionCreateContract.Intent.OnBackClicked -> onBack()
            is SolutionCreateContract.Intent.OnDescriptionChanged -> {
                _state.value = _state.value.copy(description = intent.description)
            }
            is SolutionCreateContract.Intent.OnSourceChanged -> {
                _state.value = _state.value.copy(source = intent.source)
            }
            is SolutionCreateContract.Intent.OnSolutionTypeChanged -> {
                _state.value = _state.value.copy(solutionType = intent.type)
            }
            SolutionCreateContract.Intent.OnAddSolutionClicked -> addSolution()
        }
    }

    private fun addSolution() {
        val currentState = _state.value
        if (currentState.description.isBlank()) return

        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val solution = Solution(
                    id = 0,
                    title = currentState.description.take(50),
                    desc = currentState.description,
                    solutionType = currentState.solutionType,
                    cost = Cost(energyCost = 50, timeCost = 50, moneyCost = 0),
                    aidStrength = 50,
                    result = SolutionResult.IN_PROGRESS
                )
                addSolutionUseCase(solution)
                onBack()
            } catch (e: Exception) {
                _sideEffects.emit(SolutionCreateContract.SideEffect.ShowError(e.message ?: "Unknown error"))
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
