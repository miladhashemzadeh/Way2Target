package com.vampyreworld.w2t.schallengeft

import com.arkivanov.decompose.ComponentContext

interface SChallengeComponent {
    fun onBackClicked()
}

class DefaultSChallengeComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit
) : SChallengeComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
