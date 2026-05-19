package com.vampyreworld.w2t.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.splash.SplashComponent
import com.vampyreworld.w2t.splash.SplashContract
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import way2target.composeapp.generated.resources.Res
import way2target.composeapp.generated.resources.compose_multiplatform

@Composable
fun SplashScreen(component: SplashComponent) {
    LaunchedEffect(Unit) {
        // Wait for 7 seconds as requested
        delay(7000)
        component.onIntent(SplashContract.Intent.OnFinished)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}
