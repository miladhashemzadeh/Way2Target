package com.vampyreworld.w2t.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.targetft.component.TargetComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Splash(val component: SplashComponent) : Child()
        data class Home(val component: HomeComponent) : Child()
        data class Target(val component: TargetComponent) : Child()
    }

    interface SplashComponent
    interface HomeComponent
}
