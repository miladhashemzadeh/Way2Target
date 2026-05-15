package com.vampyreworld.w2t.prefrencesft

import com.arkivanov.decompose.ComponentContext

interface PrefrencesComponent {
    fun onBackClicked()
}

class DefaultPrefrencesComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit
) : PrefrencesComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
