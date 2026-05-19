package com.vampyreworld.w2t.splash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface SplashComponent {
    val state: Value<SplashContract.State>
    val sideEffects: Flow<SplashContract.SideEffect>
    
    fun onIntent(intent: SplashContract.Intent)
}

class DefaultSplashComponent(
    componentContext: ComponentContext,
    private val onFinished: () -> Unit
) : SplashComponent, ComponentContext by componentContext {

    private val _state = MutableValue(SplashContract.State())
    override val state: Value<SplashContract.State> = _state

    private val _sideEffects = MutableSharedFlow<SplashContract.SideEffect>()
    override val sideEffects: Flow<SplashContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: SplashContract.Intent) {
        when (intent) {
            SplashContract.Intent.OnFinished -> onFinished()
        }
    }
}
