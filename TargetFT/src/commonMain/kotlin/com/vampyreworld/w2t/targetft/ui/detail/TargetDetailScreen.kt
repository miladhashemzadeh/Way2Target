package com.vampyreworld.w2t.targetft.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalTier
import androidx.compose.ui.draw.scale
import com.vampyreworld.w2t.targetft.TargetContract
import com.vampyreworld.w2t.targetft.component.TargetComponent
import com.vampyreworld.w2t.targetft.ui.components.ChallengeListItem
import com.vampyreworld.w2t.targetft.ui.components.EmptySectionText
import com.vampyreworld.w2t.targetft.ui.components.GoalListItem
import com.vampyreworld.w2t.targetft.ui.components.SectionHeader

@Composable
fun TargetDetailScreen(
    goal: Goal,
    relatedGoals: List<Goal>,
    challenges: List<Challenges>,
    component: TargetComponent,
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeaderSection(goal)
        }
        
        item {
            ActionButtons(goal, component)
        }

        if (goal.priority > 70) {
            item {
                PriorityDecisionSection(component)
            }
        }

        if (goal.tier == GoalTier.MASTER || goal.tier == GoalTier.MILESTONE) {
            val sectionTitle = if (goal.tier == GoalTier.MASTER) "Milestones" else "Actions"
            item {
                SectionHeader(sectionTitle)
            }
            if (relatedGoals.isEmpty()) {
                item { EmptySectionText("No $sectionTitle added yet.") }
            } else {
                items(relatedGoals, key = { it.id }) { childGoal ->
                    GoalListItem(
                        goal = childGoal,
                        onDelete = { component.onIntent(TargetContract.Intent.DeleteSubGoal(it)) },
                        onReplace = { component.onIntent(TargetContract.Intent.ReplaceSubGoal(it)) },
                        onClick = { component.onIntent(TargetContract.Intent.OnGoalClick(it)) }
                    )
                }
            }
        }
        
        item {
            SectionHeader("Active Challenges")
        }
        if (challenges.isEmpty()) {
            item { EmptySectionText("No active challenges.") }
        } else {
            val sortedChallenges = challenges.sortedByDescending { it.priority }
            items(sortedChallenges, key = { it.id }) { challenge ->
                ChallengeListItem(
                    challenge = challenge,
                    onClick = { component.onIntent(TargetContract.Intent.OnChallengeClick(it)) }
                )
            }
        }
        
        if (goal.tier == GoalTier.ACTION) {
            item {
                ScheduleSection(goal, component)
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedButton(
                onClick = { component.onIntent(TargetContract.Intent.CancelGoal) },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.error.copy(alpha = 0.5f))),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.DeleteForever, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cancel Target")
            }
        }
    }
}

@Composable
private fun HeaderSection(goal: Goal) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            val icon = when(goal.tier) {
                GoalTier.MASTER -> Icons.Default.AccountTree
                GoalTier.MILESTONE -> Icons.Default.Flag
                GoalTier.ACTION -> Icons.AutoMirrored.Filled.DirectionsRun
            }
            val color = when(goal.tier) {
                GoalTier.MASTER -> MaterialTheme.colorScheme.primary
                GoalTier.MILESTONE -> MaterialTheme.colorScheme.secondary
                GoalTier.ACTION -> MaterialTheme.colorScheme.tertiary
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = color.copy(alpha = 0.1f), 
                    shape = MaterialTheme.shapes.medium, 
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) { 
                        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp)) 
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = goal.tier.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = color,
                        fontWeight = FontWeight.Bold
                    )
                    if (goal.priority > 0) {
                        Text(
                            text = "Priority: ${goal.priority}",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (goal.priority > 70) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Edit Goal */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(20.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = goal.title.ifEmpty { "Target Goal #${goal.id}" },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )
            
            if (goal.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = goal.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(
                    progress = { 0.45f }, 
                    modifier = Modifier.weight(1f).height(10.dp), 
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
                    color = color,
                    trackColor = color.copy(alpha = 0.1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "45%", 
                    style = MaterialTheme.typography.titleSmall, 
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
        }
    }
}

@Composable
private fun PriorityDecisionSection(component: TargetComponent) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
        ),
        modifier = Modifier.fillMaxWidth(),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "High Priority Target",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Critical path detected. It is highly recommended to finalize your decision or check your mood stability.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { component.onIntent(TargetContract.Intent.MakeDecision) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Make Decision", style = MaterialTheme.typography.labelLarge)
                }
                FilledTonalButton(
                    onClick = { component.onIntent(TargetContract.Intent.SetMood) },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Set Mood", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Composable
private fun ActionButtons(goal: Goal, component: TargetComponent) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(
            onClick = { component.onIntent(TargetContract.Intent.CreateChallenge) }, 
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(12.dp)
        ) {
            Icon(Icons.Default.FlashOn, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Challenge")
        }
        if (goal.tier != GoalTier.ACTION) {
            val childType = if (goal.tier == GoalTier.MASTER) "Milestone" else "Action"
            FilledTonalButton(
                onClick = { component.onIntent(TargetContract.Intent.CreateChildGoal) }, 
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(childType)
            }
        }
    }
}

@Composable
private fun ScheduleSection(goal: Goal, component: TargetComponent) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Reminders", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = goal.notificationEnabled,
                    onCheckedChange = { component.onIntent(TargetContract.Intent.UpdateGoal(goal.copy(notificationEnabled = it))) },
                    modifier = Modifier.scale(0.8f)
                )
            }
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    val scheduleText = goal.scheduling?.let { 
                        "Starts at: ${it.startTime ?: "Not set"}"
                    } ?: "No schedule set"
                    Text(scheduleText, style = MaterialTheme.typography.bodyMedium)
                }
                FilledIconButton(
                    onClick = { /* Change schedule */ },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Schedule", modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

// Removed local SectionHeader and EmptySectionText as they are now imported
// Removed local GoalListItem and ChallengeListItem as they are now imported

