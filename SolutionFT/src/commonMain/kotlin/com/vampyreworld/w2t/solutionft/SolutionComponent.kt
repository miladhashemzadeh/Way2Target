package com.vampyreworld.w2t.solutionft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface SolutionComponent {
    val state: Value<SolutionContract.State>
    val sideEffects: Flow<SolutionContract.SideEffect>
    
    fun onIntent(intent: SolutionContract.Intent)
}

class DefaultSolutionComponent(
    componentContext: ComponentContext,
    private val addSolutionUseCase: AddSolutionUseCase,
    private val onBack: () -> Unit
) : SolutionComponent, ComponentContext by componentContext {

    private val _state = MutableValue(SolutionContract.State())
    override val state: Value<SolutionContract.State> = _state

    private val _sideEffects = MutableSharedFlow<SolutionContract.SideEffect>()
    override val sideEffects: Flow<SolutionContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: SolutionContract.Intent) {
        when (intent) {
            SolutionContract.Intent.OnBackClicked -> onBack()
            is SolutionContract.Intent.OnSolutionTextChanged -> {
                _state.value = _state.value.copy(solutionText = intent.text)
            }
            SolutionContract.Intent.OnSaveClicked -> {
                // Handle save logic
            }
        }
    }
}
