package com.vampyreworld.w2t

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.vampyreworld.w2t.di.allAppModules
import com.vampyreworld.w2t.di.initKoin
import com.vampyreworld.w2t.root.DefaultRootComponent
import javax.swing.SwingUtilities

fun main() {
    initKoin(allAppModules)
    SwingUtilities.invokeLater {
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
}

