package com.vampyreworld.w2t.schallengeft.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Cost
import com.vampyreworld.w2t.schallengeft.SChallengeContract
import com.vampyreworld.w2t.schallengeft.SChallengeComponent
import com.vampyreworld.w2t.schallengeft.ui.components.ChallengeCard

@Composable
fun ChallengesListScreen(
    state: SChallengeContract.State,
    component: SChallengeComponent,
    padding: PaddingValues
) {
    var showAddDialog by remember { mutableStateOf(false) }
    
    Box(modifier = Modifier.fillMaxSize().padding(padding)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Active Challenges", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            items(state.challenges) { challenge ->
                ChallengeCard(
                    challenge = challenge,
                    onClick = { 
                        component.onIntent(SChallengeContract.Intent.OnChallengeClick(challenge.id))
                    }
                )
            }

            if (state.challenges.isEmpty()) {
                item {
                    Text("No challenges found. Keep moving forward!", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        
        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .padding(16.dp)
                .align(androidx.compose.ui.Alignment.BottomEnd)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Challenge")
        }
    }

    if (showAddDialog) {
        AddChallengeDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { title, desc ->
                val newChallenge = Challenges(
                    id = 0,
                    title = title,
                    desc = desc,
                    cost = Cost(10, 10, 0),
                    priority = 50,
                    isBarrier = false,
                    parentGoalId = state.goalId ?: 0L,
                    moodImpact = 0,
                    stabilityConditions = emptyList()
                )
                component.onIntent(SChallengeContract.Intent.OnAddChallenge(newChallenge))
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddChallengeDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Challenge") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(title, desc) },
                enabled = title.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
