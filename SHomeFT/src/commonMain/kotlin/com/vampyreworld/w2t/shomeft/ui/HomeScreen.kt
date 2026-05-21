package com.vampyreworld.w2t.shomeft.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.shomeft.HomeComponent
import com.vampyreworld.w2t.shomeft.HomeContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeComponent) {
    val state by component.state.subscribeAsState()
    var showDeleteSheet by remember { mutableStateOf<Goal?>(null) }
    var deleteGoalName by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Way2Target - Home") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { component.onIntent(HomeContract.Intent.CreateMasterGoal) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Master Goal")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text("Master Goals", style = MaterialTheme.typography.headlineSmall)
            }
            items(state.masterGoals) { goal ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable {
                        component.onIntent(HomeContract.Intent.OnMasterGoalClick(goal.id))
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Master Goal #${goal.id}", style = MaterialTheme.typography.titleMedium)
                            Text("Click to see details", style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = {
                            component.onIntent(HomeContract.Intent.CreateChallengeForMasterGoal(goal.id))
                        }) {
                            Text("Challenge", style = MaterialTheme.typography.labelSmall)
                        }
                        IconButton(onClick = { showDeleteSheet = goal }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Other Actions", style = MaterialTheme.typography.titleMedium)
            }
            item {
                Button(onClick = { component.onNavigateToMoodAdd() }) { Text("Mood Add") }
            }
            item {
                Button(onClick = { component.onNavigateToSChallenge() }) { Text("SChallenge") }
            }
            item {
                Button(onClick = { component.onNavigateToDecisionMaking() }) { Text("Decision Making") }
            }
            item {
                Button(onClick = { component.onNavigateToSolution() }) { Text("Solution") }
            }
            item {
                Button(onClick = { component.onNavigateToPreferences() }) { Text("Preferences") }
            }
            item {
                Button(onClick = { component.onNavigateToAboutUs() }) { Text("About Us") }
            }
        }

        if (showDeleteSheet != null) {
            ModalBottomSheet(onDismissRequest = { showDeleteSheet = null }) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth().padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Delete Master Goal", style = MaterialTheme.typography.headlineSmall)
                    Text("Enter 'Goal #${showDeleteSheet?.id}' to confirm")
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = deleteGoalName,
                        onValueChange = { deleteGoalName = it },
                        placeholder = { Text("Goal #${showDeleteSheet?.id}") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (deleteGoalName == "Goal #${showDeleteSheet?.id}") {
                                component.onIntent(HomeContract.Intent.DeleteMasterGoal(showDeleteSheet!!.id))
                                showDeleteSheet = null
                                deleteGoalName = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        enabled = deleteGoalName == "Goal #${showDeleteSheet?.id}"
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
