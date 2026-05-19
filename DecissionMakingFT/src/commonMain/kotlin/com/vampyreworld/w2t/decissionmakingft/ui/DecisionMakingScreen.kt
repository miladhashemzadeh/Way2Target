package com.vampyreworld.w2t.decissionmakingft.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vampyreworld.w2t.decissionmakingft.DecisionMakingComponent
import com.vampyreworld.w2t.decissionmakingft.DecisionMakingContract

@Composable
fun DecisionMakingScreen(component: DecisionMakingComponent) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { component.onIntent(DecisionMakingContract.Intent.OnBackClicked) }) {
            Text("Decision Making Screen - Go Back")
        }
    }
}
