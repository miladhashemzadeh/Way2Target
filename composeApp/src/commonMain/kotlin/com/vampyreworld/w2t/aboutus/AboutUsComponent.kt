package com.vampyreworld.w2t.aboutus

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface AboutUsComponent {
    val state: Value<AboutUsContract.State>
    val sideEffects: Flow<AboutUsContract.SideEffect>
    
    fun onIntent(intent: AboutUsContract.Intent)
}

class DefaultAboutUsComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit
) : AboutUsComponent, ComponentContext by componentContext {

    private val _state = MutableValue(AboutUsContract.State())
    override val state: Value<AboutUsContract.State> = _state

    private val _sideEffects = MutableSharedFlow<AboutUsContract.SideEffect>()
    override val sideEffects: Flow<AboutUsContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: AboutUsContract.Intent) {
        when (intent) {
            AboutUsContract.Intent.OnBackClicked -> onBack()
        }
    }
}
