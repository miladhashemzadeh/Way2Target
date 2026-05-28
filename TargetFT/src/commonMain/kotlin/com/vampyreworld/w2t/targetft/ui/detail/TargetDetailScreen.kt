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
import com.vampyreworld.w2t.targetft.TargetContract
import com.vampyreworld.w2t.targetft.component.TargetComponent

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
                items(relatedGoals) { childGoal ->
                    GoalListItem(childGoal, component)
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
            items(sortedChallenges) { challenge ->
                ChallengeListItem(challenge)
            }
        }
        
        if (goal.tier == GoalTier.ACTION) {
            item {
                ScheduleSection(goal, component)
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            TextButton(
                onClick = { component.onIntent(TargetContract.Intent.CancelGoal) },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel Target")
            }
        }
    }
}

@Composable
private fun HeaderSection(goal: Goal) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
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
                Surface(color = color.copy(alpha = 0.1f), shape = MaterialTheme.shapes.small, modifier = Modifier.size(32.dp)) {
                    Box(contentAlignment = Alignment.Center) { Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp)) }
                }
                Spacer(modifier = Modifier.width(8.dp))
                SuggestionChip(
                    onClick = {},
                    label = { Text(goal.tier.name) },
                    colors = SuggestionChipDefaults.suggestionChipColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                )
                Spacer(modifier = Modifier.weight(1f))
                if (goal.priority > 0) {
                    Badge(containerColor = MaterialTheme.colorScheme.errorContainer) {
                        Text("Priority: ${goal.priority}", modifier = Modifier.padding(4.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = goal.title.ifEmpty { "Target Goal #${goal.id}" },
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = goal.description.ifEmpty { "No description provided." },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(progress = { 0.45f }, modifier = Modifier.fillMaxWidth().height(8.dp), strokeCap = androidx.compose.ui.graphics.StrokeCap.Round)
            Text(text = "45% Complete", style = MaterialTheme.typography.labelSmall, modifier = Modifier.align(Alignment.End))
        }
    }
}

@Composable
private fun PriorityDecisionSection(component: TargetComponent) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "High Priority Target",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error
            )
            Text(
                "This goal has a high priority. It is recommended to make a formal decision or set your mood before proceeding.",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { component.onIntent(TargetContract.Intent.MakeDecision) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Make Decision")
                }
                OutlinedButton(
                    onClick = { component.onIntent(TargetContract.Intent.SetMood) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Set Mood")
                }
            }
        }
    }
}

@Composable
private fun ActionButtons(goal: Goal, component: TargetComponent) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { component.onIntent(TargetContract.Intent.CreateChallenge) }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.FlashOn, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Challenge")
        }
        if (goal.tier != GoalTier.ACTION) {
            val childType = if (goal.tier == GoalTier.MASTER) "Milestone" else "Action"
            OutlinedButton(onClick = { component.onIntent(TargetContract.Intent.CreateChildGoal) }, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(childType)
            }
        }
    }
}

@Composable
private fun ScheduleSection(goal: Goal, component: TargetComponent) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Scheduling & Notifications", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = goal.notificationEnabled,
                    onCheckedChange = { /* Update goal via component */ }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(12.dp))
                val scheduleText = goal.scheduling?.let { 
                    "Starts at: ${it.startTime ?: "Not set"}"
                } ?: "No schedule set"
                Text(scheduleText, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Change schedule */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Schedule", modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
}

@Composable
private fun EmptySectionText(text: String) {
    Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
private fun GoalListItem(goal: Goal, component: TargetComponent) {
    val icon = when(goal.tier) {
        GoalTier.MASTER -> Icons.Default.AccountTree
        GoalTier.MILESTONE -> Icons.Default.Flag
        GoalTier.ACTION -> Icons.AutoMirrored.Filled.DirectionsRun
    }
    ListItem(
        headlineContent = { Text(goal.title.ifEmpty { "${goal.tier.name} #${goal.id}" }) },
        leadingContent = { Icon(icon, contentDescription = null) },
        trailingContent = { 
            Row {
                IconButton(onClick = { component.onIntent(TargetContract.Intent.ReplaceSubGoal(goal.id)) }) { 
                    Icon(Icons.Default.SwapHoriz, contentDescription = "Replace") 
                }
                IconButton(onClick = { component.onIntent(TargetContract.Intent.DeleteSubGoal(goal.id)) }) { 
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error) 
                }
            }
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium).clickable {  }
    )
}

@Composable
private fun ChallengeListItem(challenge: Challenges) {
    ListItem(
        headlineContent = { Text(challenge.title) },
        supportingContent = { 
            Column {
                Text(challenge.desc)
                if (challenge.stabilityConditions.isNotEmpty()) {
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        challenge.stabilityConditions.take(2).forEach { condition ->
                            Surface(
                                color = if (condition.isMaintained) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
                                shape = MaterialTheme.shapes.extraSmall
                            ) {
                                Text(
                                    condition.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        leadingContent = { 
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.FlashOn, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                Text("${challenge.priority}", style = MaterialTheme.typography.labelSmall)
            }
        },
        trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
        modifier = Modifier.background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium).clickable {  }
    )
}
