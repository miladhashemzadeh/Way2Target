package com.vampyreworld.w2t.decissionmakingft

import com.arkivanov.decompose.ComponentContext
import com.vampyreworld.w2t.domain.usecase.MakeDecisionUseCase

interface DecisionMakingComponent {
    fun onBackClicked()
}

class DefaultDecisionMakingComponent(
    componentContext: ComponentContext,
    private val makeDecisionUseCase: MakeDecisionUseCase,
    private val onBack: () -> Unit
) : DecisionMakingComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
