package com.vampyreworld.w2t.targetft.component

import com.arkivanov.decompose.ComponentContext
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase

interface TargetComponent {
    fun onBackClicked()
}

class DefaultTargetComponent(
    componentContext: ComponentContext,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val onBack: () -> Unit
) : TargetComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
