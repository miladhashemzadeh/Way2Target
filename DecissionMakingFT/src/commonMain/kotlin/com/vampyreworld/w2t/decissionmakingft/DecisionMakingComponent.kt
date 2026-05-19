package com.vampyreworld.w2t.decissionmakingft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.usecase.MakeDecisionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface DecisionMakingComponent {
    val state: Value<DecisionMakingContract.State>
    val sideEffects: Flow<DecisionMakingContract.SideEffect>
    
    fun onIntent(intent: DecisionMakingContract.Intent)
}

class DefaultDecisionMakingComponent(
    componentContext: ComponentContext,
    private val makeDecisionUseCase: MakeDecisionUseCase,
    private val onBack: () -> Unit
) : DecisionMakingComponent, ComponentContext by componentContext {

    private val _state = MutableValue(DecisionMakingContract.State())
    override val state: Value<DecisionMakingContract.State> = _state

    private val _sideEffects = MutableSharedFlow<DecisionMakingContract.SideEffect>()
    override val sideEffects: Flow<DecisionMakingContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: DecisionMakingContract.Intent) {
        when (intent) {
            DecisionMakingContract.Intent.OnBackClicked -> onBack()
            is DecisionMakingContract.Intent.OnMakeDecision -> {
                // Handle make decision
            }
        }
    }
}
