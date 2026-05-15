package com.vampyreworld.w2t.aboutus

import com.arkivanov.decompose.ComponentContext

interface AboutUsComponent {
    fun onBackClicked()
}

class DefaultAboutUsComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit
) : AboutUsComponent, ComponentContext by componentContext {
    override fun onBackClicked() = onBack()
}
