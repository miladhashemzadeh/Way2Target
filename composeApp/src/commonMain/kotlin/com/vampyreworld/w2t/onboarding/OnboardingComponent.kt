package com.vampyreworld.w2t.onboarding

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface OnboardingComponent {
    val state: Value<OnboardingContract.State>
    val sideEffects: Flow<OnboardingContract.SideEffect>
    
    fun onIntent(intent: OnboardingContract.Intent)
}

class DefaultOnboardingComponent(
    componentContext: ComponentContext,
    private val onFinish: () -> Unit
) : OnboardingComponent, ComponentContext by componentContext {

    private val _state = MutableValue(OnboardingContract.State())
    override val state: Value<OnboardingContract.State> = _state

    private val _sideEffects = MutableSharedFlow<OnboardingContract.SideEffect>()
    override val sideEffects: Flow<OnboardingContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: OnboardingContract.Intent) {
        when (intent) {
            OnboardingContract.Intent.OnFinishClicked -> onFinish()
            is OnboardingContract.Intent.OnPageChanged -> {
                _state.value = _state.value.copy(currentPage = intent.page)
            }
        }
    }
}
