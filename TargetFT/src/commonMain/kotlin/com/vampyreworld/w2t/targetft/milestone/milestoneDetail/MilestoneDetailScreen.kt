package com.vampyreworld.w2t.targetft.milestone.milestoneDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

@Composable
fun MilestoneDetailScreen(
    component: MilestoneDetailContract.Component,
    padding: PaddingValues
) {
    val state by component.state.subscribeAsState()
    val goal = state.selectedGoal ?: return
    val colors = LocalAppColorScheme.current
    
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            W2THeader(
                title = "Milestone Detail",
                subtitle = "Sub-targets lead to big wins",
                avatarText = "M"
            )
        }

        item {
            W2TCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        W2TStatusChip(text = "Ongoing", backgroundColor = colors.accent)
                        val parentId = state.parentId
                        if (parentId != null) {
                            Text(
                                text = "Parent Goal ›", 
                                style = MaterialTheme.typography.bodySmall, 
                                color = colors.muted,
                                modifier = Modifier.clickable { component.onIntent(MilestoneDetailContract.Intent.OnGoalClick(parentId, "MASTER")) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = goal.title,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    W2TProgressBar(progress = 0.5f, color = colors.accent)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "50% Complete",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.accent,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        item {
            Button(
                onClick = { component.onIntent(MilestoneDetailContract.Intent.NavigateToChallengeList) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(Icons.Default.FlashOn, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("ساختن چالش برای این هدف")
            }
        }

        item {
            W2TCard {
                W2TSectionTitle("Action Goals (${state.actions.size})")
                if (state.actions.isEmpty()) {
                    Text(
                        text = "No actions defined for this milestone.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.muted,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    state.actions.forEach { action ->
                        W2TActionItem(
                            title = action.title,
                            subtitle = goal.title,
                            time = if (action.status == GoalStatus.COMPLETED) "Completed" else "Due Tomorrow",
                            checked = action.status == GoalStatus.COMPLETED,
                            onCheckedChange = { isChecked ->
                                component.onIntent(MilestoneDetailContract.Intent.UpdateGoal(
                                    action.withStatus(if (isChecked) GoalStatus.COMPLETED else GoalStatus.ACTIVE)
                                ))
                            },
                            onClick = { component.onIntent(MilestoneDetailContract.Intent.OnGoalClick(action.id, "ACTION")) }
                        )
                    }
                }
            }
        }

        if (state.challenges.isNotEmpty()) {
            item {
                W2TCard {
                    W2TSectionTitle("Active Challenges (${state.challenges.size})")
                    state.challenges.forEach { challenge ->
                        W2TChallengeCard(
                            title = challenge.title,
                            goalTitle = goal.title,
                            description = challenge.desc,
                            status = if (challenge.status == GoalStatus.COMPLETED) "Finished" else "Ongoing",
                            modifier = Modifier.clickable { }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }

        item {
            Button(
                onClick = { component.onIntent(MilestoneDetailContract.Intent.CreateAction) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent.copy(alpha = 0.1f), contentColor = colors.accent),
                shape = RoundedCornerShape(28.dp),
                elevation = null
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Action")
            }
        }

        item {
            TextButton(
                onClick = { component.onIntent(MilestoneDetailContract.Intent.DeleteGoal) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete Milestone")
            }
        }
        
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
