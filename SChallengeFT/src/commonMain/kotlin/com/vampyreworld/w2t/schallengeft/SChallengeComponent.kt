package com.vampyreworld.w2t.schallengeft

import com.arkivanov.decompose.ComponentContext
import com.vampyreworld.w2t.domain.usecase.AddChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase

interface SChallengeComponent {
    fun onBackClicked()
}

class DefaultSChallengeComponent(
    componentContext: ComponentContext,
    private val addChallengeUseCase: AddChallengeUseCase,
    private val getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit
) : SChallengeComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
