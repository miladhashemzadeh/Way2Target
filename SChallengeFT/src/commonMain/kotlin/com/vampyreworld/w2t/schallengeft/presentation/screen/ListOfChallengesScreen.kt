package com.vampyreworld.w2t.schallengeft.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Challenges

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfChallengesScreen(
    goalId: Long,
    challenges: List<Challenges>,
    onBack: () -> Unit,
    onAddChallenge: () -> Unit,
    onChallengeClick: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Challenges for Goal #$goalId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddChallenge) {
                Icon(Icons.Default.Add, contentDescription = "Add Challenge")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (challenges.isEmpty()) {
                Text("No challenges found", modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    items(challenges) { challenge ->
                        ChallengeItem(challenge = challenge, onClick = { onChallengeClick(challenge.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun ChallengeItem(challenge: Challenges, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = challenge.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Priority: ${challenge.priority}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
