package com.vampyreworld.w2t.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vampyreworld.w2t.home.HomeComponent

@Composable
fun HomeScreen(component: HomeComponent) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Home Screen")
        Button(onClick = { component.onNavigateToTarget() }) { Text("Target") }
        Button(onClick = { component.onNavigateToMoodAdd() }) { Text("Mood Add") }
        Button(onClick = { component.onNavigateToSChallenge() }) { Text("SChallenge") }
        Button(onClick = { component.onNavigateToDecisionMaking() }) { Text("Decision Making") }
        Button(onClick = { component.onNavigateToSolution() }) { Text("Solution") }
        Button(onClick = { component.onNavigateToPreferences() }) { Text("Preferences") }
        Button(onClick = { component.onNavigateToAboutUs() }) { Text("About Us") }
    }
}
