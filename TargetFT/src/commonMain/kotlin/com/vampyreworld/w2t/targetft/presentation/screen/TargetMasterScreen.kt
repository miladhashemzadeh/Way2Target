package com.vampyreworld.w2t.targetft.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.GoalTier
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.targetft.presentation.component.TargetMasterComponent
import com.vampyreworld.w2t.targetft.presentation.intent.TargetMasterIntent

@Composable
fun TargetMasterScreen(component: TargetMasterComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { component.onIntent(TargetMasterIntent.OnAddGoalClick) },
                containerColor = colors.accent,
                contentColor = MaterialTheme.colorScheme.surface,
                shape = androidx.compose.foundation.shape.CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Goal")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = colors.accent)
            } else if (state.error != null) {
                Text(
                    text = state.error!!, 
                    color = MaterialTheme.colorScheme.error, 
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item {
                        W2THeader(
                            title = "Good morning!",
                            subtitle = "Ready to crush your goals?",
                            avatarText = "A"
                        )
                    }

                    item {
                        W2TMoodWidget(
                            title = "How are you feeling?",
                            description = "Quick check to optimize your decision-making.",
                            buttonText = "Check Mood",
                            onButtonClick = { /* Navigate to Mood */ }
                        )
                    }

                    item {
                        W2TCard {
                            W2TSectionTitle("Your Master Goals")
                            val masterGoals = state.goals.filter { it.tier == GoalTier.MASTER }
                            if (masterGoals.isEmpty()) {
                                Text("No master goals yet.", style = MaterialTheme.typography.bodyMedium, color = colors.muted)
                            } else {
                                masterGoals.forEach { goal ->
                                    W2TGoalItem(
                                        icon = "🎯",
                                        title = goal.title,
                                        progress = 0.7f,
                                        progressText = "70% Complete",
                                        onClick = { component.onIntent(TargetMasterIntent.OnGoalClick(goal.id)) }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        W2TAiInsightsCard(
                            title = "AI Insights",
                            description = "Your current progress suggests focusing on high-impact milestones.",
                            buttonText = "View Strategy",
                            onButtonClick = { }
                        )
                    }
                }
            }
        }
    }
}
