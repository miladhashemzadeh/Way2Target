package com.vampyreworld.w2t.prefrencesft.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vampyreworld.w2t.prefrencesft.PrefrencesComponent
import com.vampyreworld.w2t.prefrencesft.PrefrencesContract

@Composable
fun PrefrencesScreen(component: PrefrencesComponent) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { component.onIntent(PrefrencesContract.Intent.OnBackClicked) }) {
            Text("Prefrences Screen - Go Back")
        }
    }
}
