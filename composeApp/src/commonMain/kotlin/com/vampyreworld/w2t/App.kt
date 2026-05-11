package com.vampyreworld.w2t

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.vampyreworld.w2t.root.RootComponent
import com.vampyreworld.w2t.sharedui.theme.W2TTheme

@Composable
fun App(root: RootComponent) {
    W2TTheme {
        Children(
            stack = root.childStack,
            animation = stackAnimation(fade()),
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.Splash -> {
                    Text("Splash Screen")
                }
                is RootComponent.Child.Home -> {
                    Text("Home Screen")
                }
            }
        }
    }
}
