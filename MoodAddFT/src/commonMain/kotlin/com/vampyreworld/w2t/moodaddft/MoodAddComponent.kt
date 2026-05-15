package com.vampyreworld.w2t.moodaddft

import com.arkivanov.decompose.ComponentContext

interface MoodAddComponent {
    fun onBackClicked()
}

class DefaultMoodAddComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit
) : MoodAddComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
