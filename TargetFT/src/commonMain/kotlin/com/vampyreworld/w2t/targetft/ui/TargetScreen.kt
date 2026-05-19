package com.vampyreworld.w2t.targetft.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vampyreworld.w2t.targetft.TargetContract
import com.vampyreworld.w2t.targetft.component.TargetComponent

@Composable
fun TargetScreen(component: TargetComponent) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { component.onIntent(TargetContract.Intent.OnBackClicked) }) {
            Text("Target Screen - Go Back")
        }
    }
}
