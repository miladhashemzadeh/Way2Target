package com.vampyreworld.w2t.solutionft

import com.arkivanov.decompose.ComponentContext

interface SolutionComponent {
    fun onBackClicked()
}

class DefaultSolutionComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit
) : SolutionComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
