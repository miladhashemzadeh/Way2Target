package com.vampyreworld.w2t.schallengeft.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.schallengeft.SChallengeComponent
import com.vampyreworld.w2t.schallengeft.SChallengeContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SChallengeScreen(component: SChallengeComponent) {
    val state by component.state.subscribeAsState()
    val challenge = state.selectedChallenge

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(challenge?.title ?: "Challenges") },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(SChallengeContract.Intent.OnBackClicked) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (challenge == null) {
            ChallengesList(state, component, padding)
        } else {
            ChallengeDetail(challenge, component, padding)
        }
    }
}

@Composable
fun ChallengesList(state: SChallengeContract.State, component: SChallengeComponent, padding: PaddingValues) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
        item {
            Text("Select a Challenge", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        items(state.challenges) { item ->
            ListItem(
                headlineContent = { Text(item.title) },
                modifier = Modifier.clickable { 
                    // In a real app we would send an intent to select this challenge
                }
            )
        }

        item {
            Button(onClick = { 
                // component.onIntent(...) // Add dummy challenge for demo
            }) {
                Text("Add Dummy Challenge")
            }
        }
    }
}

@Composable
fun ChallengeDetail(challenge: Challenges, component: SChallengeComponent, padding: PaddingValues) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
        item {
            Text("Challenge Details: ${challenge.title}", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Status: Ongoing (Dummy)")
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { component.onIntent(SChallengeContract.Intent.OnMakeDecision) }) {
                    Text("Make Decision")
                }
                Button(onClick = { component.onIntent(SChallengeContract.Intent.OnTakeAiHelp) }) {
                    Text("AI Help")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { component.onIntent(SChallengeContract.Intent.OnAddSolution) }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Solution")
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Solutions", style = MaterialTheme.typography.titleMedium)
        }
        
        items(listOf("Solution A", "Solution B")) { solution ->
            ListItem(headlineContent = { Text(solution) })
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Change Status", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Button(onClick = { component.onIntent(SChallengeContract.Intent.OnStatusChange("Finished")) }, modifier = Modifier.weight(1f)) { Text("Done", style = MaterialTheme.typography.labelSmall) }
                Button(onClick = { component.onIntent(SChallengeContract.Intent.OnStatusChange("Failed")) }, modifier = Modifier.weight(1f)) { Text("Fail", style = MaterialTheme.typography.labelSmall) }
                Button(onClick = { component.onIntent(SChallengeContract.Intent.OnStatusChange("Cancelled")) }, modifier = Modifier.weight(1f)) { Text("Cancel", style = MaterialTheme.typography.labelSmall) }
            }
        }
    }
}
