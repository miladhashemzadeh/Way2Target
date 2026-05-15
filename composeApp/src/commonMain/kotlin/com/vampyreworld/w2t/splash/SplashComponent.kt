package com.vampyreworld.w2t.splash

import com.arkivanov.decompose.ComponentContext

interface SplashComponent {
    fun onSplashFinished()
}

class DefaultSplashComponent(
    componentContext: ComponentContext,
    private val onFinished: () -> Unit
) : SplashComponent, ComponentContext by componentContext {
    override fun onSplashFinished() = onFinished()
}
