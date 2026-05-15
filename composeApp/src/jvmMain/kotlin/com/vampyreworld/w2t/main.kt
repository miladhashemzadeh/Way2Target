package com.vampyreworld.w2t

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vampyreworld.w2t.di.allAppModules
import com.vampyreworld.w2t.di.initKoin

fun main() {
    initKoin(allAppModules)
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Way2Target",
        ) {
            App()
        }
    }
}
