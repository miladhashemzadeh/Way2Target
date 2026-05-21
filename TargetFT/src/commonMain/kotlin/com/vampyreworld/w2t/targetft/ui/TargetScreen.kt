package com.vampyreworld.w2t.targetft.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalTier
import com.vampyreworld.w2t.targetft.TargetContract
import com.vampyreworld.w2t.targetft.component.TargetComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetScreen(component: TargetComponent) {
    val state by component.state.subscribeAsState()
    val goal = state.selectedGoal

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(goal?.tier?.name ?: "New Target") },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(TargetContract.Intent.OnBackClicked) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (goal == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Create New Target Form Here")
            }
        } else {
            when (goal.tier) {
                GoalTier.MASTER -> MasterGoalDetail(goal, component, padding)
                GoalTier.MILESTONE -> MilestoneGoalDetail(goal, component, padding)
                GoalTier.ACTION -> ActionGoalDetail(goal, component, padding)
            }
        }
    }
}

@Composable
fun MasterGoalDetail(goal: Goal, component: TargetComponent, padding: PaddingValues) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
        item {
            Text("Master Goal Details #${goal.id}", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { component.onIntent(TargetContract.Intent.CreateMilestone) }, modifier = Modifier.weight(1f)) {
                    Text("Add Milestone", style = MaterialTheme.typography.labelSmall)
                }
                Button(onClick = { component.onIntent(TargetContract.Intent.CreateChallenge) }, modifier = Modifier.weight(1f)) {
                    Text("New Challenge", style = MaterialTheme.typography.labelSmall)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /* Navigate to last challenge */ }, modifier = Modifier.fillMaxWidth()) {
                Text("View Last Challenge")
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Related Milestones", style = MaterialTheme.typography.titleMedium)
        }
        // Fake milestones
        items(listOf("Milestone 1", "Milestone 2")) { milestone ->
            ListItem(headlineContent = { Text(milestone) })
        }
    }
}

@Composable
fun MilestoneGoalDetail(goal: Goal, component: TargetComponent, padding: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
        Text("Milestone Goal Details #${goal.id}", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Evaluation: 75% complete (Dummy)")
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { component.onIntent(TargetContract.Intent.CreateChallenge) }, modifier = Modifier.weight(1f)) {
                Text("New Challenge", style = MaterialTheme.typography.labelSmall)
            }
            Button(onClick = { /* Navigate to add solution */ }, modifier = Modifier.weight(1f)) {
                Text("Add Solution", style = MaterialTheme.typography.labelSmall)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Navigate to last challenge */ }, modifier = Modifier.fillMaxWidth()) {
            Text("View Last Milestone Challenge")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { component.onIntent(TargetContract.Intent.CancelGoal) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel Milestone")
        }
    }
}

@Composable
fun ActionGoalDetail(goal: Goal, component: TargetComponent, padding: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
        Text("Action Goal Details #${goal.id}", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Schedules and Notifications settings here.")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { component.onIntent(TargetContract.Intent.CancelGoal) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Cancel Action")
        }
    }
}
