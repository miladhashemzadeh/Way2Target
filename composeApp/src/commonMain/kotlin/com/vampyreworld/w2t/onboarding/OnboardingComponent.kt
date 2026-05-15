package com.vampyreworld.w2t.onboarding

import com.arkivanov.decompose.ComponentContext

interface OnboardingComponent {
    fun onFinished()
}

class DefaultOnboardingComponent(
    componentContext: ComponentContext,
    private val onFinish: () -> Unit
) : OnboardingComponent, ComponentContext by componentContext {
    override fun onFinished() = onFinish()
}
