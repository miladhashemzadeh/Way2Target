package com.vampyreworld.w2t.aboutus.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vampyreworld.w2t.aboutus.AboutUsComponent

@Composable
fun AboutUsScreen(component: AboutUsComponent) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { component.onBackClicked() }) {
            Text("About Us Screen - Go Back")
        }
    }
}
