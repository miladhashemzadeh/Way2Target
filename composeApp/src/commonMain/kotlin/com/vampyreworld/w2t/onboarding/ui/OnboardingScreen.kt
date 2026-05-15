package com.vampyreworld.w2t.onboarding.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vampyreworld.w2t.onboarding.OnboardingComponent

@Composable
fun OnboardingScreen(component: OnboardingComponent) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { component.onFinished() }) {
            Text("Onboarding Screen - Finish")
        }
    }
}
