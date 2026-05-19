package com.vampyreworld.w2t.targetft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.targetft.store.TargetStore

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
