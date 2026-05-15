package com.vampyreworld.w2t.splash.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vampyreworld.w2t.splash.SplashComponent
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(component: SplashComponent) {
    LaunchedEffect(Unit) {
        delay(2000)
        component.onSplashFinished()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Splash Screen")
    }
}
