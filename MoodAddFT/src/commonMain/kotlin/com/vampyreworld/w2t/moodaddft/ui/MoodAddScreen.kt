package com.vampyreworld.w2t.moodaddft.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.UserMood
import com.vampyreworld.w2t.moodaddft.MoodAddComponent
import com.vampyreworld.w2t.moodaddft.MoodAddContract
import com.vampyreworld.w2t.moodaddft.ui.components.MoodSlider
import kotlinx.datetime.Clock

@Composable
fun MoodAddScreen(component: MoodAddComponent) {
    val state by component.state.subscribeAsState()
    var energy by remember { mutableStateOf(70f) }
    var focus by remember { mutableStateOf(60f) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Quick Check-in",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            "How's your current state of mind?",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))

        if (state.isAlreadyAddedToday) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "You've already checked in today!",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                "Come back tomorrow for your next check-in.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { component.onIntent(MoodAddContract.Intent.OnBackClicked) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Go Back", style = MaterialTheme.typography.titleMedium)
            }
        } else {
            MoodSlider(
                label = "Energy Level",
                value = energy,
                onValueChange = { energy = it },
                icon = Icons.Default.Bolt
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            MoodSlider(
                label = "Focus & Concentration",
                value = focus,
                onValueChange = { focus = it },
                icon = Icons.Default.Psychology
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Button(
                onClick = { 
                    component.onIntent(MoodAddContract.Intent.OnAddMood(
                        UserMood(
                            kotlinx.datetime.Clock.System.now().toEpochMilliseconds(), 
                            energy.toInt(), 
                            50, 
                            focus.toInt(), 
                            50, 
                            50
                        )
                    ))
                    component.onIntent(MoodAddContract.Intent.OnBackClicked) 
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Save & Continue", style = MaterialTheme.typography.titleMedium)
            }

            TextButton(
                onClick = { component.onIntent(MoodAddContract.Intent.OnBackClicked) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("I'll do this later")
            }
        }
    }
}
