package com.vampyreworld.w2t.solutionft.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vampyreworld.w2t.solutionft.SolutionComponent

@Composable
fun SolutionScreen(component: SolutionComponent) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { component.onBackClicked() }) {
            Text("Solution Screen - Go Back")
        }
    }
}
