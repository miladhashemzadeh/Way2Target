package com.vampyreworld.w2t.targetft.component

import com.arkivanov.decompose.ComponentContext

interface TargetComponent {
    fun onBackClicked()
}

class DefaultTargetComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit
) : TargetComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
