package com.vampyreworld.w2t.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Splash(val component: SplashComponent) : Child()
        data class Home(val component: HomeComponent) : Child()
        // Add more children as needed based on Screens.kt
    }

    // Temporary placeholder interfaces for children
    interface SplashComponent
    interface HomeComponent
}
