package com.vampyreworld.w2t.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.vampyreworld.navigation.Screens
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Screens>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Screens.serializer(),
            initialConfiguration = Screens.Splash,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: Screens, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Screens.Splash -> RootComponent.Child.Splash(object : RootComponent.SplashComponent {})
            is Screens.Home -> RootComponent.Child.Home(object : RootComponent.HomeComponent {})
            else -> RootComponent.Child.Splash(object : RootComponent.SplashComponent {}) // Fallback
        }
}
