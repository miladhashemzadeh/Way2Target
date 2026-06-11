package com.vampyreworld.w2t.decissionmakingft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.usecase.SaveDecisionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface DecisionMakingComponent {
    val state: Value<DecisionMakingContract.State>
    val sideEffects: Flow<DecisionMakingContract.SideEffect>
    
    fun onIntent(intent: DecisionMakingContract.Intent)
}

class DefaultDecisionMakingComponent(
    componentContext: ComponentContext,
    private val saveDecisionUseCase: SaveDecisionUseCase,
    private val onBack: () -> Unit
) : DecisionMakingComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(DecisionMakingContract.State())
    override val state: Value<DecisionMakingContract.State> = _state

    private val _sideEffects = MutableSharedFlow<DecisionMakingContract.SideEffect>()
    override val sideEffects: Flow<DecisionMakingContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: DecisionMakingContract.Intent) {
        when (intent) {
            DecisionMakingContract.Intent.OnBackClicked -> onBack()
            is DecisionMakingContract.Intent.OnMakeDecision -> {
                makeDecision(intent)
            }
        }
    }

    private fun makeDecision(intent: DecisionMakingContract.Intent.OnMakeDecision) {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                saveDecisionUseCase(intent.decision)
                onBack()
            } catch (e: Exception) {
                _sideEffects.emit(DecisionMakingContract.SideEffect.ShowError(e.message ?: "Failed to save decision"))
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
