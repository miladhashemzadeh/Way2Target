package com.vampyreworld.w2t.moodaddft

import com.arkivanov.decompose.ComponentContext
import com.vampyreworld.w2t.domain.usecase.AddMoodUseCase
import com.vampyreworld.w2t.domain.usecase.GetMoodHistoryUseCase

interface MoodAddComponent {
    fun onBackClicked()
}

class DefaultMoodAddComponent(
    componentContext: ComponentContext,
    private val addMoodUseCase: AddMoodUseCase,
    private val getMoodHistoryUseCase: GetMoodHistoryUseCase,
    private val onBack: () -> Unit
) : MoodAddComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
