package com.vampyreworld.w2t.solutionft.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.solutionft.SolutionComponent
import com.vampyreworld.w2t.solutionft.SolutionContract

@Composable
fun SolutionScreen(component: SolutionComponent) {
    val state by component.state.subscribeAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { component.onIntent(SolutionContract.Intent.OnBackClicked) }) {
            Text("Solution Screen - Go Back")
        }
    }
}
