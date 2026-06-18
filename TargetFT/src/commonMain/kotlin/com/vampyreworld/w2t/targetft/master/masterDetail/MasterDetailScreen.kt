package com.vampyreworld.w2t.targetft.master.masterDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterDetailScreen(
    component: MasterDetailContract.Component,
    padding: PaddingValues
) {
    val state by component.state.subscribeAsState()
    val goal = state.selectedGoal ?: return
    val colors = LocalAppColorScheme.current
    
    var isEditing by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf(goal.title) }
    var editedDescription by remember { mutableStateOf(goal.description) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            W2THeader(
                title = "جزئیات هدف",
                subtitle = "آماده‌ای برای رسیدن به اهدافت؟"
            )
        }

        item {
            W2TCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (isEditing) {
                        OutlinedTextField(
                            value = editedTitle,
                            onValueChange = { editedTitle = it },
                            label = { Text("Title") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = editedDescription,
                            onValueChange = { editedDescription = it },
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    val updatedGoal = when(goal) {
                                        is com.vampyreworld.w2t.domain.data.model.MasterGoal -> goal.copy(title = editedTitle, description = editedDescription)
                                        is com.vampyreworld.w2t.domain.data.model.MilestoneGoal -> goal.copy(title = editedTitle, description = editedDescription)
                                        is com.vampyreworld.w2t.domain.data.model.ActionGoal -> goal.copy(title = editedTitle, description = editedDescription)
                                    }
                                    component.onIntent(MasterDetailContract.Intent.UpdateGoal(updatedGoal))
                                    isEditing = false
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Save") }
                            OutlinedButton(onClick = { isEditing = false }, modifier = Modifier.weight(1f)) { Text("Cancel") }
                        }
                    } else {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = goal.title,
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { isEditing = true }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = colors.accent)
                            }
                        }
                        Text(text = goal.description, style = MaterialTheme.typography.bodyMedium, color = colors.muted)
                        Spacer(modifier = Modifier.height(16.dp))
                        W2TProgressBar(progress = 0.7f, color = colors.accent)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "70% Complete",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.accent,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        item {
            Button(
                onClick = { component.onIntent(MasterDetailContract.Intent.NavigateToChallengeList) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(Icons.Default.FlashOn, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Create Challenges")
            }
        }

        item {
            W2TCard {
                W2TSectionTitle("Goal Breakdown")
                
                W2TTreeNode(
                    icon = "🎯", 
                    title = "Master Goal: ${goal.title}", 
                    type = "master", 
                    onClick = { /* Already here */ }
                ) {
                    state.milestones.forEach { milestone ->
                        W2TTreeNode(
                            icon = "✨", 
                            title = "Milestone: ${milestone.title}", 
                            type = "milestone", 
                            onClick = { component.onIntent(MasterDetailContract.Intent.OnGoalClick(milestone.id, "MILESTONE")) }
                        ) {
                        }
                    }
                }
            }
        }

        if (state.challenges.isNotEmpty()) {
            item {
                W2TCard {
                    W2TSectionTitle("Active Challenges")
                    state.challenges.forEach { challenge ->
                        W2TChallengeCard(
                            title = challenge.title,
                            goalTitle = goal.title,
                            description = challenge.desc,
                            status = if (challenge.status == GoalStatus.COMPLETED) "Finished" else "Ongoing",
                            modifier = Modifier.clickable { 
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }

        item {
            W2TAiInsightsCard(
                title = "AI Insights",
                description = "Your current progress suggests focusing on practical projects for faster skill acquisition.",
                buttonText = "View Strategy",
                onButtonClick = { }
            )
        }

        item {
            Button(
                onClick = { component.onIntent(MasterDetailContract.Intent.CreateMilestone) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent.copy(alpha = 0.1f), contentColor = colors.accent),
                shape = RoundedCornerShape(28.dp),
                elevation = null
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Milestone")
            }
        }

        item {
            TextButton(
                onClick = { component.onIntent(MasterDetailContract.Intent.DeleteGoal) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete Goal")
            }
        }
        
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
