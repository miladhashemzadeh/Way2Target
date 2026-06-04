package com.vampyreworld.w2t.prefrencesft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.vampyreworld.w2t.domain.usecase.prefrences.GetThemeUseCase
import com.vampyreworld.w2t.domain.usecase.prefrences.SetThemeUseCase
import com.vampyreworld.w2t.sharedui.arch.componentScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface PrefrencesComponent {
    val state: Value<PrefrencesContract.State>
    val sideEffects: Flow<PrefrencesContract.SideEffect>
    
    fun onIntent(intent: PrefrencesContract.Intent)
}

class DefaultPrefrencesComponent(
    componentContext: ComponentContext,
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val onBack: () -> Unit
) : PrefrencesComponent, ComponentContext by componentContext {

    private val _state = MutableValue(PrefrencesContract.State())
    override val state: Value<PrefrencesContract.State> = _state

    private val _sideEffects = MutableSharedFlow<PrefrencesContract.SideEffect>()
    override val sideEffects: Flow<PrefrencesContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        getThemeUseCase()
            .onEach { isDarkMode ->
                _state.update { it.copy(isDarkMode = isDarkMode) }
            }
            .launchIn(componentScope())
    }

    override fun onIntent(intent: PrefrencesContract.Intent) {
        when (intent) {
            PrefrencesContract.Intent.OnBackClicked -> onBack()
            is PrefrencesContract.Intent.OnThemeChanged -> {
                setThemeUseCase(intent.isDarkMode)
            }
        }
    }
}
