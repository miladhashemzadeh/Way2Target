package com.vampyreworld.w2t.moodaddft.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.UserMood
import com.vampyreworld.w2t.moodaddft.MoodAddComponent
import com.vampyreworld.w2t.moodaddft.MoodAddContract

@Composable
fun MoodAddScreen(component: MoodAddComponent) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("How are you feeling?", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Rate your Energy, Focus, and Creativity", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        // Simple sliders for hollow app
        var energy by remember { mutableStateOf(50f) }
        Text("Energy: ${energy.toInt()}%")
        Slider(value = energy, onValueChange = { energy = it }, valueRange = 0f..100f)
        
        var focus by remember { mutableStateOf(50f) }
        Text("Focus: ${focus.toInt()}%")
        Slider(value = focus, onValueChange = { focus = it }, valueRange = 0f..100f)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { 
                component.onIntent(MoodAddContract.Intent.OnAddMood(
                    UserMood(0L, energy.toInt(), 50, focus.toInt(), 50, 50)
                ))
                component.onIntent(MoodAddContract.Intent.OnBackClicked) 
            }
        ) {
            Text("Save Mood and Continue")
        }

        TextButton(onClick = { component.onIntent(MoodAddContract.Intent.OnBackClicked) }) {
            Text("Skip for now")
        }
    }
}
