package com.vampyreworld.w2t

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.vampyreworld.w2t.di.initKoin
import com.vampyreworld.w2t.root.DefaultRootComponent

fun MainViewController() = ComposeUIViewController {
    initKoin()
    val root = DefaultRootComponent(DefaultComponentContext(LifecycleRegistry()))
    App(root)
}
