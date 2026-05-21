package com.vampyreworld.w2t.shomeft.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.shomeft.HomeComponent
import com.vampyreworld.w2t.shomeft.HomeContract
import com.vampyreworld.w2t.shomeft.ui.components.MasterGoalCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeComponent) {
    val state by component.state.subscribeAsState()
    var showDeleteSheet by remember { mutableStateOf<Goal?>(null) }
    var deleteGoalName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Way2Target") },
                actions = {
                    IconButton(onClick = { component.onIntent(HomeContract.Intent.OnProfileClick) }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { component.onIntent(HomeContract.Intent.CreateMasterGoal) },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("New Master Goal") },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(bottom = 80.dp, start = 16.dp, end = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Hello, ${state.userName}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Track your long-term goals and stay focused.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            item {
                Text(
                    text = "Your Master Goals",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (state.masterGoals.isEmpty()) {
                item {
                    Text(
                        "No master goals yet. Start by creating one!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 32.dp)
                    )
                }
            } else {
                items(state.masterGoals) { goal ->
                    MasterGoalCard(
                        goal = goal,
                        onClick = { component.onIntent(HomeContract.Intent.OnMasterGoalClick(goal.id)) },
                        onDelete = { showDeleteSheet = goal },
                        onChallenge = { component.onIntent(HomeContract.Intent.CreateChallengeForMasterGoal(goal.id)) }
                    )
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(32.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Quick Access", style = MaterialTheme.typography.titleMedium)
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { component.onNavigateToMoodAdd() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Add Mood")
                    }
                    OutlinedButton(
                        onClick = { component.onNavigateToPreferences() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Settings")
                    }
                }
            }

            // Adding extra space at the end to ensure FAB doesn't hide content
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        if (showDeleteSheet != null) {
            ModalBottomSheet(onDismissRequest = { showDeleteSheet = null }) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth().padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Delete Master Goal?", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "This action cannot be undone. All milestones and actions will be lost.",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Type 'Goal #${showDeleteSheet?.id}' to confirm", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = deleteGoalName,
                        onValueChange = { deleteGoalName = it },
                        placeholder = { Text("Goal #${showDeleteSheet?.id}") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (deleteGoalName == "Goal #${showDeleteSheet?.id}") {
                                component.onIntent(HomeContract.Intent.DeleteMasterGoal(showDeleteSheet!!.id))
                                showDeleteSheet = null
                                deleteGoalName = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        enabled = deleteGoalName == "Goal #${showDeleteSheet?.id}",
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Delete Permanently")
                    }
                }
            }
        }
    }
}
