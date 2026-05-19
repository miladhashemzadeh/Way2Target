package com.vampyreworld.w2t.targetft.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.targetft.presentation.component.TargetMasterComponent
import com.vampyreworld.w2t.targetft.presentation.intent.TargetMasterIntent
import com.vampyreworld.w2t.domain.data.model.Goal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetMasterScreen(component: TargetMasterComponent) {
    val state by component.state.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Targets") },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(TargetMasterIntent.OnBackClick) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { component.onIntent(TargetMasterIntent.OnAddGoalClick) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Goal")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                Text(text = state.error!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    items(state.goals) { goal ->
                        GoalItem(goal = goal, onClick = { component.onIntent(TargetMasterIntent.OnGoalClick(goal.id)) })
                    }
                }
            }
        }
    }
}

@Composable
fun GoalItem(goal: Goal, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Goal #${goal.id}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Tier: ${goal.tier}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
