package com.vampyreworld.w2t.solutionft

import com.arkivanov.decompose.ComponentContext
import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase

interface SolutionComponent {
    fun onBackClicked()
}

class DefaultSolutionComponent(
    componentContext: ComponentContext,
    private val addSolutionUseCase: AddSolutionUseCase,
    private val onBack: () -> Unit
) : SolutionComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
