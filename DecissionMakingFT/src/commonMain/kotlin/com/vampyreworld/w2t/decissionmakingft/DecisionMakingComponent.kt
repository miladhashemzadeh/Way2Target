package com.vampyreworld.w2t.decissionmakingft

import com.arkivanov.decompose.ComponentContext

interface DecisionMakingComponent {
    fun onBackClicked()
}

class DefaultDecisionMakingComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit
) : DecisionMakingComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
