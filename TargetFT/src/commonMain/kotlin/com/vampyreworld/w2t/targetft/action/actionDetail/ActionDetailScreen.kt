package com.vampyreworld.w2t.targetft.action.actionDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionDetailScreen(
    component: ActionDetailContract.Component,
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
                title = "Action Detail",
                subtitle = "Consistency is key",
                avatarText = "A"
            )
        }

        item {
            W2TCard {
                Column {
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
                                    component.onIntent(ActionDetailContract.Intent.UpdateGoal(updatedGoal))
                                    isEditing = false
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Save") }
                            OutlinedButton(onClick = { isEditing = false }, modifier = Modifier.weight(1f)) { Text("Cancel") }
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Surface(
                                color = if (goal.status == GoalStatus.COMPLETED) colors.success else colors.challengeColor,
                                shape = CircleShape
                            ) {
                                Text(
                                    text = if (goal.status == GoalStatus.COMPLETED) "Completed" else "Pending",
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = { isEditing = true }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = colors.accent)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = goal.title,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = goal.description.ifEmpty { "Apply foundational knowledge to create functional components." },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            lineHeight = 24.sp
                        )
                    }
                }
            }
        }

        item {
            W2TCard {
                W2TSectionTitle("Details")
                W2TDetailRow("Due Date", "Tomorrow, 5:00 PM")
            }
        }

        item {
            Button(
                onClick = { component.onIntent(ActionDetailContract.Intent.NavigateToChallengeList) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(Icons.Default.FlashOn, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Create Challenges")
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
                                component.onIntent(ActionDetailContract.Intent.OnChallengeClick(challenge.id))
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
                description = "Focus on completing this task today to maintain your 5-day streak!",
                buttonText = "View Strategy",
                onButtonClick = { }
            )
        }

        item {
            TextButton(
                onClick = { component.onIntent(ActionDetailContract.Intent.DeleteGoal) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete Action")
            }
        }
        
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
