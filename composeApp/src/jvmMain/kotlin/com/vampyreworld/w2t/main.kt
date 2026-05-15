package com.vampyreworld.w2t

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.vampyreworld.w2t.root.DefaultRootComponent

fun main() {
    val lifecycle = LifecycleRegistry()
    val root = DefaultRootComponent(DefaultComponentContext(lifecycle))

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Way2Target",
        ) {
            App(root)
        }
    }
}