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

        if (goal.tier == GoalTier.MASTER || goal.tier == GoalTier.MILESTONE) {
            item {
                SectionHeader("Sub-Goals / Milestones")
            }
            if (relatedGoals.isEmpty()) {
                item { EmptySectionText("No milestones added yet.") }
            } else {
                items(relatedGoals) { milestone ->
                    GoalListItem(milestone)
                }
            }
        }
        
        item {
            SectionHeader("Active Challenges")
        }
        if (challenges.isEmpty()) {
            item { EmptySectionText("No active challenges.") }
        } else {
            items(challenges) { challenge ->
                ChallengeListItem(challenge)
            }
        }
        
        if (goal.tier == GoalTier.ACTION) {
            item {
                ScheduleSection()
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
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Target Goal #${goal.id}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Description of the target goes here. This is a focus area that requires consistent effort to achieve results.",
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
private fun ActionButtons(goal: Goal, component: TargetComponent) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { component.onIntent(TargetContract.Intent.CreateChallenge) }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.FlashOn, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Challenge")
        }
        if (goal.tier != GoalTier.ACTION) {
            OutlinedButton(onClick = { component.onIntent(TargetContract.Intent.CreateMilestone) }, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Text("Milestone")
            }
        }
    }
}

@Composable
private fun ScheduleSection() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Schedules", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Daily at 09:00 AM", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Weekdays Only", style = MaterialTheme.typography.bodyMedium)
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
private fun GoalListItem(goal: Goal) {
    ListItem(
        headlineContent = { Text("Milestone #${goal.id}") },
        leadingContent = { Icon(Icons.Default.OutlinedFlag, contentDescription = null) },
        trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
        modifier = Modifier.background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium).clickable {  }
    )
}

@Composable
private fun ChallengeListItem(challenge: Challenges) {
    ListItem(
        headlineContent = { Text(challenge.title) },
        supportingContent = { Text(challenge.desc) },
        leadingContent = { Icon(Icons.Default.FlashOn, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary) },
        trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
        modifier = Modifier.background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium).clickable {  }
    )
}
