package com.vampyreworld.w2t.prefrencesft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface PrefrencesComponent {
    val state: Value<PrefrencesContract.State>
    val sideEffects: Flow<PrefrencesContract.SideEffect>
    
    fun onIntent(intent: PrefrencesContract.Intent)
}

class DefaultPrefrencesComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit
) : PrefrencesComponent, ComponentContext by componentContext {

    private val _state = MutableValue(PrefrencesContract.State())
    override val state: Value<PrefrencesContract.State> = _state

    private val _sideEffects = MutableSharedFlow<PrefrencesContract.SideEffect>()
    override val sideEffects: Flow<PrefrencesContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: PrefrencesContract.Intent) {
        when (intent) {
            PrefrencesContract.Intent.OnBackClicked -> onBack()
            is PrefrencesContract.Intent.OnThemeChanged -> {
                _state.value = _state.value.copy(isDarkMode = intent.isDarkMode)
            }
        }
    }
}
