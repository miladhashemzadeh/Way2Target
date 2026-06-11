package com.vampyreworld.w2t.targetft.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vampyreworld.w2t.domain.data.model.ActionGoal
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.domain.data.model.GoalTier
import com.vampyreworld.w2t.domain.data.model.MilestoneGoal
import com.vampyreworld.w2t.domain.data.model.MasterGoal
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.targetft.TargetContract
import com.vampyreworld.w2t.targetft.component.TargetComponent

@Composable
fun TargetDetailScreen(
    goal: Goal,
    relatedGoals: List<Goal>,
    component: TargetComponent,
    padding: PaddingValues
) {
    val colors = LocalAppColorScheme.current
    
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            W2THeader(
                title = if (goal.tier != GoalTier.MASTER) goal.title else "Goal Detail",
                subtitle = "Ready to crush your goals?",
                avatarText = "A"
            )
        }

        if (goal is ActionGoal) {
            item {
                ActionDetailHeader(goal, relatedGoals, colors, component)
            }
            item {
                W2TCard {
                    W2TSectionTitle("Details")
                    val milestone = relatedGoals.find { it.id == goal.milestoneGoalId }
                    val masterGoal = if (milestone is MilestoneGoal) {
                        relatedGoals.find { it.id == milestone.masterGoalId }
                    } else null
                    
                    W2TDetailRow("Master Goal", masterGoal?.title ?: "Not set") {
                        masterGoal?.id?.let { component.onIntent(TargetContract.Intent.OnGoalClick(it)) }
                    }
                    W2TDetailRow("Milestone", milestone?.title ?: "Not set") {
                        milestone?.id?.let { component.onIntent(TargetContract.Intent.OnGoalClick(it)) }
                    }
                    W2TDetailRow("Due Date", "Tomorrow, 5:00 PM")
                    W2TDetailRow("Reminders", if (goal.notificationEnabled) "Enabled" else "Disabled")
                }
            }
        } else {
            item {
                val headerTitle = if (goal.tier == GoalTier.MASTER) goal.title else "Milestone: ${goal.title}"
                W2TCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (goal.tier == GoalTier.MILESTONE) {
                            val masterGoal = relatedGoals.find { it.tier == GoalTier.MASTER }
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                W2TStatusChip(text = "Ongoing", backgroundColor = colors.accent)
                                if (masterGoal != null) {
                                    Text(
                                        text = "Goal: ${masterGoal.title} ›", 
                                        style = MaterialTheme.typography.bodySmall, 
                                        color = colors.muted,
                                        modifier = Modifier.clickable { component.onIntent(TargetContract.Intent.OnGoalClick(masterGoal.id)) }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        
                        Text(
                            text = headerTitle,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
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

            item {
                W2TCard {
                    val sectionTitle = if (goal.tier == GoalTier.MASTER) "Goal Breakdown" else "Action Goals"
                    W2TSectionTitle(sectionTitle)
                    
                    if (goal.tier == GoalTier.MASTER) {
                        W2TTreeNode(icon = "🎯", title = "Master Goal: ${goal.title}", type = "master", onClick = { component.onIntent(TargetContract.Intent.OnGoalClick(goal.id)) }) {
                            relatedGoals.filter { it.tier == GoalTier.MILESTONE && it.upperGoalId == goal.id }.forEach { milestone ->
                                W2TTreeNode(icon = "✨", title = "Milestone: ${milestone.title}", type = "milestone", onClick = { component.onIntent(TargetContract.Intent.OnGoalClick(milestone.id)) }) {
                                    relatedGoals.filter { it.tier == GoalTier.ACTION && it.upperGoalId == milestone.id }.forEach { action ->
                                        W2TTreeNode(
                                            icon = "", title = action.title, type = "action",
                                            completed = action.status == GoalStatus.COMPLETED,
                                            onCheckedChange = { isChecked ->
                                                component.onIntent(TargetContract.Intent.UpdateGoal(
                                                    action.withStatus(if (isChecked) GoalStatus.COMPLETED else GoalStatus.ACTIVE)
                                                ))
                                            },
                                            onClick = { component.onIntent(TargetContract.Intent.OnGoalClick(action.id)) }
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        // Milestone view: Flat list of actions
                        relatedGoals.filter { it.tier == GoalTier.ACTION && it.upperGoalId == goal.id }.forEach { action ->
                            W2TActionItem(
                                title = action.title,
                                subtitle = goal.title,
                                time = if (action.status == GoalStatus.COMPLETED) "Completed" else "Due Tomorrow",
                                checked = action.status == GoalStatus.COMPLETED,
                                onCheckedChange = { isChecked ->
                                    component.onIntent(TargetContract.Intent.UpdateGoal(
                                        action.withStatus(if (isChecked) GoalStatus.COMPLETED else GoalStatus.ACTIVE)
                                    ))
                                },
                                onClick = { component.onIntent(TargetContract.Intent.OnGoalClick(action.id)) }
                            )
                        }
                    }
                }
            }
        }

        if (component.state.value.challenges.isNotEmpty()) {
            item {
                W2TCard {
                    W2TSectionTitle("Active Challenges")
                    component.state.value.challenges.forEach { challenge ->
                        W2TChallengeCard(
                            title = challenge.title,
                            goalTitle = goal.title,
                            description = challenge.desc,
                            status = if (challenge.status == GoalStatus.COMPLETED) "Finished" else "Ongoing",
                            modifier = Modifier.clickable { 
                                component.onIntent(TargetContract.Intent.OnChallengeClick(challenge.id))
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { component.onIntent(TargetContract.Intent.NavigateToChallengeList) },
                    modifier = Modifier.weight(1f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Icon(Icons.Default.FlashOn, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Challenges")
                }
                
                if (goal.tier != GoalTier.ACTION) {
                    Button(
                        onClick = { component.onIntent(TargetContract.Intent.CreateChildGoal) },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colors.accent.copy(alpha = 0.1f), contentColor = colors.accent),
                        shape = RoundedCornerShape(28.dp),
                        elevation = null
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (goal.tier == GoalTier.MASTER) "Milestone" else "Action")
                    }
                }
            }
        }
        
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun ActionDetailHeader(goal: Goal, relatedGoals: List<Goal>, colors: com.vampyreworld.w2t.sharedui.theme.color.AppColorScheme, component: TargetComponent) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Surface(
                color = if (goal.status == GoalStatus.COMPLETED) colors.success else colors.challengeColor,
                shape = CircleShape
            ) {
                Text(
                    text = if (goal.status == GoalStatus.COMPLETED) "Completed" else "Pending",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            val milestone = relatedGoals.find { it.tier == GoalTier.MILESTONE }
            if (milestone != null) {
                Text(
                    text = "Milestone: ${milestone.title} ›",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.muted,
                    modifier = Modifier.clickable { component.onIntent(TargetContract.Intent.OnGoalClick(milestone.id)) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = goal.description.ifEmpty { "Apply foundational knowledge to create functional components." },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = 24.sp
        )
    }
}
